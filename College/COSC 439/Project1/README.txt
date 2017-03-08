1) Log into emunix through your preferred ssh client
2) Upload the contents of this zip file
3) Type the following commands to compile the scripts
	gcc -o UDPEchoClient UDPEchoClient.c DieWithError.c 
	gcc -o UDPEchoServer UDPEchoServer.c DieWithError.c

4) Type the following commands to run the compiled scripts
	./server <PortNumber>
	./client <IPofServer> <PortNumber>

5) From there, you are prompted with a list of commands you can type in.
	Ex:Login 56

Keep in mind, all parameters for commands must be on the same line and all commands must start with a capital letter. For example:
This will work: Login 56
This will not: Login
56  
This will not: login 56        