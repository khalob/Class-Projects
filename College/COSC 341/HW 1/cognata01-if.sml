(* NAME: Khalob Cognata *)
(* FILE: Assignment One *)
(* DATE:    2/7/2016    *)

(* HW NUMBER: #1 *)
(* FUNCTION NAME: (dispnthc) display nth character *)
(* DESCRIPTION:   Displays the nth character of a string. *)
fun dispnthc(s, n) =
    if (n = 1) then hd(explode s)
    else dispnthc(implode(tl(explode s)), n-1);

(* HW NUMBER: #2 *)
(* FUNCTION NAME: (delnthc) delete nth character *)
(* DESCRIPTION:   Deletes the nth character of a string. Returns string without character. *)
fun delnthc(s, n) =
    if (null(explode (s))) then ""
    else if (n = 1) then implode(tl(explode s))
    else str(hd(explode s)) ^ delnthc (implode(tl(explode s)), n - 1); 

(* HW NUMBER: #3 *)
(* FUNCTION NAME: (multin) multiple in *)
(* DESCRIPTION:   Takes a list of three [a,b,c] and multiplies a by b for c times. *)
fun multin(L : int list) =
    if (hd(tl(tl L)) = ~1) then nil
    else hd(L) :: multin([hd L * hd(tl L), hd(tl L), (hd(tl(tl L))) - 1]);

(* HW NUMBER: #4 *)
(* FUNCTION NAME: (remv) remove *)
(* DESCRIPTION:   Removes all occurances of a given element within a given list. *)
fun remv(s, L) =
    if null(L) then nil
    else if (s = hd L) then remv(s, tl L)
    else hd(L) :: remv(s, tl L);

(* HW NUMBER: #5 *)
(* FUNCTION NAME: (remvdub) remove dubplicate *)
(* DESCRIPTION:   Removes all duplicate occurances of an element within a given list. *)
fun remvdub(L) =
    if null(L) then nil
    else hd L::remvdub(remv(hd L, L));

(* HW NUMBER: #6 *)
(* FUNCTION NAME: (int2str) integer to string *)
(* DESCRIPTION:   Converts an integer to a string. *)
fun int2str(n) = 
    if n=0 then ""
    else int2str(floor(real n / 10.0)) ^ 
         str(chr(round(((real n / 10.0) - 
         real(floor(real n / 10.0))) * 10.0) + 48));

(* HW NUMBER: #7 *)
(* FUNCTION NAME: (str2intHelper) str2intHelper *)
(* DESCRIPTION:   Returns 1 or if length > 1 then 10 ^ (length of given string - 1). *)
fun str2intHelper(s) =
   if null(tl(explode s)) then 1
   else if null(tl(tl(explode s))) then 10
   else 10 * str2intHelper(implode(tl(explode s)));

(* HW NUMBER: #7 *)
(* FUNCTION NAME: (str2int) string to integer *)
(* DESCRIPTION:   Converts an string to a integer. *)
fun str2int(s) =
    if s="" then 0
    else ((ord(hd(explode s)) - 48) * ( str2intHelper(s))) +  
    str2int(implode(tl(explode s)));

(* HW NUMBER: #8 *)
(* FUNCTION NAME: (diff2Helper) diff2Helper *)
(* DESCRIPTION:    Returns true if a given element is within a given list. *)
fun diff2Helper(a, L) =
    if null(L) then false
    else if hd(L) = a then true
    else diff2Helper(a, tl L);

(* HW NUMBER: #8 *)
(* FUNCTION NAME: (diff2) difference two *)
(* DESCRIPTION:    Returns the set difference of two given lists. *)
fun diff2(L1, L2) = 
    if null(L1) then nil
    else if not(diff2Helper(hd(L1), L2)) then hd(L1)::diff2(tl L1, L2)
    else diff2(tl L1, L2);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (addIndex) add index *)
(* DESCRIPTION:   Changes list to (Z * int) list. The int being the index of the element. *)
fun addIndex (i, L) = 
    if null L then nil
    else (hd L, i ) :: addIndex(i+1, tl L);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (fst / snd ) first / second *)
(* DESCRIPTION:   Returns the first and second value of a (int * int) respectively. *)
fun fst (x,y) = x
fun snd (x,y) = y

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (indeTuple) index tuple *)
(* DESCRIPTION:   Finds the index of a given element within a (Z * int) list. *)
fun indeTuple(n, L) =
    if null L then nil
    else if (n = fst(hd L)) then snd(hd L) :: indeTuple(n, tl L)
    else indeTuple(n, tl L);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (inde) inde *)
(* DESCRIPTION:   Finds the index of a given element within list. *)
fun inde(n, L) = indeTuple(n, addIndex(1,L));

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occrRemover) occrRemover *)
(* DESCRIPTION:   Returns the list with all of a given element removed. *)
fun occrRemover(L, n) = 
    if null(L) then nil
    else if (n = hd L) then  occrRemover(tl L, n)
    else hd L :: occrRemover(tl L, n);

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occrHelper) occrHelper *)
(* DESCRIPTION:   Returns the amount of a given element in a list. *)
fun occrHelper(L, n) = 
    if null(L) then 0
    else if (n = hd L) then 1 + occrHelper(tl L, n)
    else occrHelper(tl L, n);

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occr) occurence *)
(* DESCRIPTION:    Displays the occurence of an element of a list. *)
fun occr(L : int list) = 
    if null(L) then nil
    else (hd L, occrHelper(L, hd L)) :: occr(occrRemover(L, hd L));

(* HW NUMBER: #11 *)
(* FUNCTION NAME: (neleHelper) neleHelper *)
(* DESCRIPTION:   Repeats each element in a list n times. *) 
fun neleHelper(L, b, c) =
    if null L then nil
    else if b=1 then (hd L) :: neleHelper(tl L, c, c)
    else (hd L) :: neleHelper(L, b-1, c);

(* HW NUMBER: #11 *)
(* FUNCTION NAME: (nele) n elements *)
(* DESCRIPTION:   Repeats each element in a list n times. *) 
fun nele(L, n) =
    if null L then nil
    else neleHelper(L, n, n);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrinREV) ntrinREV *)
(* DESCRIPTION:   Reverses a list. *)
fun ntrinREV (L1, L2) =
    if null(L1) then L2
    else ntrinREV (tl L1, (hd L1)::L2);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrinHelper) ntrinHelper *)
(* DESCRIPTION:   Generates the triangular of a given number. *)
fun ntrinHelper(n: int) =
    if n=1 then 1
    else (ntrinHelper(n-1) + n);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrin) n triangle *)
(* DESCRIPTION:   Generates a list of n triangular numbers from 1 to triangluar(n) in reverse. *)
fun ntrinL(n: int) = 
    if n=0 then nil
    else   ntrinHelper(n) :: ntrinL(n-1);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrin) n triangle *)
(* DESCRIPTION:   Generates a list of n triangular numbers from 1 to triangluar(n). *)
fun ntrin(n)=
    ntrinREV(ntrinL(n), nil);

(* HW NUMBER: #13 *)
(* FUNCTION NAME: (isfactHelper) isfactHelper *)
(* DESCRIPTION:   Divides n by a given x until n is 1 or something else. (factorial or not factorial) *)
fun isfactHelper(n, x) = 
    if not((n mod x) = 0) andalso not(n=1)then false
    else if n=1 then true
    else isfactHelper(n div x, x + 1) ;

(* HW NUMBER: #13 *)
(* FUNCTION NAME: (isfact) is factorial *)
(* DESCRIPTION:   Returns true if the number given a is a factorial number. *)
fun isfact(n) =
    isfactHelper(n, 2); 

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliatHead) split at head *)
(* DESCRIPTION:   Returns a string from index 0 to index n. *)
fun spliatHead(s : string, n) = 
    if n=0 then ""
    else str(hd(explode(s))) ^ spliatHead(implode(tl(explode(s))), n-1);

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliatTail) split at tail *)
(* DESCRIPTION:   Returns a string from index n to the end of the string. *)
fun spliatTail(s, n) =
    if n=0 then s
    else spliatTail(implode(tl(explode(s))), n-1);

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliat) split at *)
(* DESCRIPTION:   Returns a list of 2 parts of a string that was split at a given position of n. *)
fun spliat(s, n) = 
    [spliatHead(s,n) , spliatTail(s,n)];

(* HW NUMBER: #15 *)
(* FUNCTION NAME: (inseachHelper) inseachHelper *)
(* DESCRIPTION:   Returns a list list with an element to each position of a list. *)
fun inseachHelper(n, L1, L2:int list) =
    if null L2 then [L1@n::L2]
    else (L1 @ (n :: L2)) :: inseachHelper(n, L1 @ [hd L2] , tl L2);

(* HW NUMBER: #15 *)
(* FUNCTION NAME: (inseach) inseach*)
(* DESCRIPTION:   Driver for insearchHelper. *)
fun inseach(n, L:int list) =
    inseachHelper(n, [], L);

dispnthc("abcdef", 4);
delnthc("abcdef", 4);
multin([2,3,5]);
remv("a", ["a","b","a","c"]);
remvdub(["a","b","a","c", "b", "a"]);
int2str(1234);
str2int("1234");
diff2([1,2,3],[2,3,4]);
inde(1, [1,2,1,1,2,2,1]);
occr([1,2,1,2,3,2]);
nele([1,2],3);
ntrin(7);
isfact(120);
spliat("Program", 3);
inseach(4,[1,2,3]);
