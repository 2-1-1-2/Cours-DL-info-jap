/*
 ============================================================================
 Name        : incrementation_simultanee_data.h
 Author      : Leonard (Lenny) NAMOLARU

  le contenu de la m�moire partag�e
 ============================================================================
 */

#ifndef INCREMENTATION_SIMULTANEE_DATA_H_
#define INCREMENTATION_SIMULTANEE_DATA_H_

#include <pthread.h>
typedef struct memory {
	pthread_mutex_t mutex;
	int number;
} memory ;

#endif /* INCREMENTATION_SIMULTANEE_DATA_H_ */
