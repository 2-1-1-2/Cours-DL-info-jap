/*
 ============================================================================
 Name        : barriere_avec_variables_de_condition.c
 Author      : Leonard (Lenny) NAMOLARU
 gcc barriere_avec_variables_de_condition.c -pthread -o barriere_avec_variables_de_condition
 ============================================================================
 */
#include <stdlib.h> // rand(), srand(), atoi(), exit(), EXIT_SUCCESS, EXIT_FAILURE
#include <unistd.h> // fork(), getpid(), sleep()
#include <stdio.h> // fprintf(), perror()
#include <sys/mman.h> // mmap()
#include <pthread.h> // pthread_mutexattr_init(), pthread_mutexattr_setpshared(), pthread_mutex_init()
#include <sys/wait.h> // wait()
#include <errno.h>
#include <string.h>


/**
 * Modifiez votre solution de l'exercice pr�c�dent en utilisant des variables
 * de condition (via pthread_cond_wait) plut�t qu'une attente active.
 */

/**
 * un type barriere_t avec :
 * - un mutex servant � prot�ger les parties critiques du code,
 * - le nombre de processus qui ne sont pas encore arriv�s � la barri�re (au d�but cet entier vaut bien entendu N),
 * - un entier qui vaudra vrai si et seulement si le barri�re peut �tre ouverte,
 */
typedef struct barriere_t {
	pthread_mutex_t mutex;
	int nb_processus_pas_encore_arrives_barriere;
	pthread_cond_t barriere_peut_etre_ouverte;
} barriere_t ;

#define SIZE_MEMOIRE_BARRIERE sizeof(barriere_t)

/**
 * une fonction void barriere(barriere_t*) sera appel�e exactement
 * une fois dans chaque processus, et v�rifiera la propri�t� suivante:
 *
 * si un processus n'est pas le dernier � atteindre la barri�re, alors
 * il doit attendre jusqu'� ce que le dernier processus atteigne la
 * barri�re pour pouvoir continuer; c'est-�-dire que la fonction
 * barriere doit bloquer le processus appelant jusqu'� ce que le
 * dernier processus appelle barriere;
 * lorsque le dernier processus appelle barriere, tous les processus
 * sont d�bloqu�s simultan�ment.
 */
void barriere(barriere_t* barriere_struct) {
	int can_continue = 0;

	/* Cf : sch�ma simplifi� d'utilisation de mutex et condition, cours 7 */

	// Chaque fils affichera un message � l'�cran avec son pid � son arriv�e � la
	// barri�re et juste apr�s l'avoir franchie
	printf("Le fils avec pid = %d est arrivee a la barriere \n", getpid());

	int mutex_lock_result = pthread_mutex_lock( &(barriere_struct->mutex) );
	if(mutex_lock_result != 0) {
		char* error_msg = strerror( mutex_lock_result ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_mutex_lock() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	/* SC - DEBUT */
	(barriere_struct->nb_processus_pas_encore_arrives_barriere)--;

	if(barriere_struct->nb_processus_pas_encore_arrives_barriere == 0)
       can_continue = 1;

	/* attendre la condition, tjrs dans la boucle */

	while( ! can_continue ){
	   // int pthread_cond_wait(pthread_cond_t *restrict cond, pthread_mutex_t *restrict mutex);
	  // Returns: 0 if OK, error number on failure
		int result_value = pthread_cond_wait( &(barriere_struct->barriere_peut_etre_ouverte), &(barriere_struct->mutex) );
		if(result_value != 0) {
			char* error_msg = strerror( result_value ); // char * strerror (int errnum)
			fprintf(stderr, "Function pthread_cond_wait() : %s \n", error_msg);
			exit (EXIT_FAILURE);
		}

		if(barriere_struct->nb_processus_pas_encore_arrives_barriere == 0)
			can_continue = 1;
	}

	// Chaque fils affichera un message � l'�cran avec son pid � son arriv�e � la
	// barri�re et juste apr�s l'avoir franchie
	printf("Le fils avec pid = %d a franchie la barriere \n", getpid());
	/* SC - FIN */

	int mutex_unlock_result = pthread_mutex_unlock( &(barriere_struct->mutex) );
	if(mutex_unlock_result != 0) {
		char* error_msg = strerror( mutex_unlock_result ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_mutex_unlock() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	/* signaler la sortie de la section critique
	 * � tous les processus suspendu sur la condition */

	// int pthread_cond_signal(pthread_cond_t *cond);
	// Returns: 0 if OK, error number on failure
	int result_value =  pthread_cond_signal( &(barriere_struct->barriere_peut_etre_ouverte) );
	if(result_value != 0) {
		char* error_msg = strerror( result_value ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_cond_signal() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}
}

int main(int argc, char* argv[]) {
	// Un processus p�re initialisera la structure barriere_t et son
	// contenu puis cr�era N processus fils gr�ce l'appel syst�me fork;

	if (argc < 2) {
		fprintf(stderr,"Utilisation : %s N \n", argv[0]);
		exit(EXIT_FAILURE);
	}

	int N = atoi(argv[1]); // int atoi (const char *string)

	// void * mmap (void *address, size_t length,int protect, int flags, int filedes, off_t offset)
	barriere_t* ptr_barriere_t = mmap((void *) 0, SIZE_MEMOIRE_BARRIERE, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANON, -1 , 0);
	if( (void*) ptr_barriere_t == MAP_FAILED) {
		perror("Function mmap()"); // void perror (const char *message)
		exit(EXIT_FAILURE);
	}

	ptr_barriere_t->nb_processus_pas_encore_arrives_barriere = N;

	 // int pthread_mutexattr_init(pthread_mutexattr_t *attr);
	pthread_mutexattr_t attr;
	int mutexattr_init_result = pthread_mutexattr_init(&attr);
	if(mutexattr_init_result != 0) {
		// pour r�cuperer le massage correspondant � l�erreur,
		// faut passer par strerror() ; La fonction perror() est
		// inutilisable avec les mutex

		char* error_msg = strerror( mutexattr_init_result ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_mutexattr_init() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	// int pthread_mutexattr_setpshared(pthread_mutexattr_t *attr,int pshared);
	int mutexattr_setpshared_result = pthread_mutexattr_setpshared(&attr, PTHREAD_PROCESS_SHARED);
	if(mutexattr_setpshared_result != 0) {
		char* error_msg = strerror( mutexattr_setpshared_result ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_mutexattr_setpshared() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	// int pthread_mutex_init(pthread_mutex_t *mutex, const pthread_mutexattr_t *attr);
	int mutex_init_result = pthread_mutex_init( &(ptr_barriere_t->mutex), &attr);
	if(mutex_init_result != 0) {
		char* error_msg = strerror( mutex_init_result ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_mutex_init() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	pthread_condattr_t condattr;

	// int pthread_condattr_init(pthread_condattr_t *attr);
	// Return: 0 if OK, error number on failure
	int result_value = pthread_condattr_init(&condattr);
	if(result_value != 0) {
		char* error_msg = strerror( result_value ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_condattr_init() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}


	// int pthread_condattr_setpshared(pthread_condattr_t *attr, int pshared);
	// pshared: PTHREAD_PROCESS_PRIVATE, PTHREAD_PROCESS_SHARED
	// Return: 0 if OK, error number on failure
	result_value = pthread_condattr_setpshared(&condattr ,PTHREAD_PROCESS_SHARED);
	if(result_value != 0) {
		char* error_msg = strerror( result_value ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_condattr_setpshared() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	// int pthread_cond_init(pthread_cond_t *restrict cond, const pthread_condattr_t *restrict attr);
	// Return: 0 if OK, error number on failure
	result_value = pthread_cond_init(&(ptr_barriere_t->barriere_peut_etre_ouverte) ,&condattr);
	if(result_value != 0) {
		char* error_msg = strerror( result_value ); // char * strerror (int errnum)
		fprintf(stderr, "Function pthread_cond_init() : %s \n", error_msg);
		exit (EXIT_FAILURE);
	}

	int i;
	pid_t fork_result;
	for(i = 0 ; i < N ; i++) {
		fork_result = fork();
		if(fork_result == -1) {
			perror("Function fork()"); // void perror (const char *message)
			exit(EXIT_FAILURE);
		}

		if(fork_result == 0) { // FILS
			/**
			 * Chacun des processus fils attendra un temps al�atoire de quelques
			 * secondes avant de faire un appel � barriere.
			 */

			// void srand (unsigned int seed)
			srand( getpid() ); // pid_t getpid (void)

			int min = 2, max = 20;
			// int rand (void)
			int sec = (rand() % (max - min +1)) + min;

			// unsigned int sleep (unsigned int seconds)
			sleep( sec );
			barriere(ptr_barriere_t);
			return EXIT_SUCCESS;
		}
	}

	/* PERE */
	pid_t wait_result;
	while (( wait_result = wait(NULL)) > 0 ); // pid_t wait (int *status-ptr)

	return EXIT_SUCCESS;
}

/**
 * Via la commande top, vous surveillerez la consommation CPU des
processus en attente. Comparez avec la solution de l'exercice pr�c�dent.
 *
 */

/**
 * La solution de l'exercice pr�c�dent
 * Reponse : Bcp de CPU....
 *   PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND
  648 lenny     20   0    2648    100      0 R  98.7   0.0   0:10.32 barriereDeSynch
  650 lenny     20   0    2648    100      0 R  98.3   0.0   0:05.34 barriereDeSynch
  651 lenny     20   0    2648    100      0 R  98.3   0.0   0:10.33 barriereDeSynch
 */

/**
 * Mtn , 0% CPU
 *684 lenny     20   0    2516    616    536 S   0.0   0.0   0:00.00 barriere_avec_v
  685 lenny     20   0    2648    100      0 S   0.0   0.0   0:00.00 barriere_avec_v
  686 lenny     20   0    2648    100      0 S   0.0   0.0   0:00.00 barriere_avec_v
  687 lenny     20   0    2516    100      0 S   0.0   0.0   0:00.00 barriere_avec_v
  688 lenny     20   0    2648    100      0 S   0.0   0.0   0:00.00 barriere_avec_v
  689 lenny     20   0    2648    100      0 S   0.0   0.0   0:00.00 barriere_avec_v
 */
