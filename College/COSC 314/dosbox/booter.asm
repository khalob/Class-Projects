;bit16					; 16bit by default
org 0x7c00
jmp short start
nop
bsOEM	db "OS423 v.0.1"               ; OEM String

start:
;;cls
	mov ah,06h		;Function 06h (scroll screen)
	mov al,0		;Scroll all lines
	mov bh,0ah		;Attribute (lightgreen on black) 
	mov ch,0		;Upper left row is zero
	mov cl,0		;Upper left column is zero
	mov dh,24		;Lower left row is 24
	mov dl,79		;Lower left column is 79
	int 10h			;BIOS Interrupt 10h (video services)

;;printmsg
	mov ah,13h		;Function 13h (display string), XT machine only
	mov al,1		;Write mode is zero: cursor stay after last char
	mov bh,0		;Use video page of zero
	mov bl,03h		;Attribute (lightgreen on black)
	mov cx,mlen		;Character string length
	mov dh,0		;Position on row 0
	mov dl,0		;And column 0
	lea bp,[msg]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

;;print box
	mov cx,lnlen1		;Character string length
	mov al,0		;Write mode is zero: cursor stay after last char
	mov dh,10		;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line1]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
	
	mov cx,horzlinelen		;Character string length
	mov dh,10		;Position on row 0
	mov dl,25		;And column 0
	lea bp,[horzline]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

	mov cx,lnlen2		;Character string length
	mov dh,10 	;Position on row 0
	mov dl,55		;And column 0
	lea bp,[line2]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
	
	mov cx,lnlen3		;Character string length
	mov dh,11 	;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line3]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

	mov cx,lnlen4		;Character string length
	mov dh,12 	;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line4]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

	mov cx,lnlen9		;Character string length
	mov al,0		;Write mode is zero: cursor stay after last char
	mov dh,13		;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line9]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

	mov cx,lnlen9		;Character string length
	mov dh,13 	;Position on row 0
	mov dl,55		;And column 0
	lea bp,[line9]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h

	mov cx,lnlen5		;Character string length
	mov dh,14 	;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line5]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
	
	mov cx,lnlen6		;Character string length
	mov dh,15 	;Position on row 0
	mov dl,24		;And column 0
	lea bp,[line6]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
	
	mov cx,horzlinelen		;Character string length
	mov dh,15 	;Position on row 0
	mov dl,25		;And column 0
	lea bp,[horzline]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
	
	mov cx,lnlen7		;Character string length
	mov dh,15 	;Position on row 0
	mov dl,55		;And column 0
	lea bp,[line7]	;Load the offset address of string into BP, es:bp
					;Same as mov bp, msg  
	int 10h
;;Jump to date file
goToDate:
    xor ax, ax ; clear ax
    mov ds, ax  ; DS=0

    mov ax, ds
    mov es, ax  ; es == ds

    xor bx,bx   ;Ensure that the buffer offset is 0!
    mov ah,0x2  ;2 = Read floppy
    mov al,0x1  ;Reading one sector
    mov ch,1    ; Cylinder 0
    mov cl,2    ; Sector 2
    mov dh,0    ; Head 0
    mov dl,0  ; floppy drive
    mov bx,0x5678 
    mov es,bx   ; 
    xor bx, bx          ; BX = 0. So ES:BX=0x5678:0x0000
    mov bx,0x1234 ; BX = 0x1234. So ES:BX=0x5678:0x1234
    int 13h

    jc goToDate ;try again if it fails
    jmp  word 0x5678:0x1234

;;Data

msg db 'OS423, a real OS, version 0.1 (c) compsci emich 2017 ...', 10, 13, '$'
mlen equ $-msg

line1 db  201
lnlen1 equ $-line1

horzline db 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205, 205
horzlinelen equ $-horzline

line2 db  187, 10, 13
lnlen2 equ $-line2

line3 db  186,  '    O S 4 2 3, (c) 2 0 1 7    ',  186,  10, 13
lnlen3 equ $-line3

line4 db  186,  '       -Khalob Cognata-       ',  186,  10, 13
lnlen4 equ $-line4

line5 db  186, ' Press any key to continue... ',  186,  10, 13
lnlen5 equ $-line5

line6 db  200
lnlen6 equ $-line6

line7 db  188, 10, 13
lnlen7 equ $-line7

line8 db  '$'
lnlen8 equ $-line8

line9 db  186
lnlen9 equ $-line9

padding	times 510-($-$$) db 0		;to make MBR 512 bytes
bootSig	db 0x55, 0xaa		;signature (optional)
