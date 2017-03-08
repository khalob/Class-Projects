#include <stdio.h>      /* for printf() and fprintf() */
#include <sys/socket.h> /* for recv() and send() */
#include <unistd.h>     /* for close() */
#include <stdlib.h>
#include <string.h>

#define RCVBUFSIZE 16   /* Size of receive buffer */

void DieWithError(char *errorMessage);  /* Error handling function */

char* getClientMsg(int clntSocket){
    char* echoBuffer;        			/* Buffer for echo string */
    int recvMsgSize;                    /* Size of received message */
	echoBuffer = malloc(RCVBUFSIZE);
	
    /* Receive message from client */
    if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE, 0)) < 0)
        DieWithError("recv() failed");

	return echoBuffer;
}

void sendMessageToClient(int clntSocket, char* msg){
	/* Send Message To Client */
	send(clntSocket, msg, strlen(msg)+1, 0);
}
