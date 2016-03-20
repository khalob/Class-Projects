(* NAME: Khalob Cognata *)
(* FILE: Assignment One *)
(* DATE:    2/7/2016    *)

(* HW NUMBER: #1 *)
(* FUNCTION NAME: (dispnthc) display nth character *)
(* DESCRIPTION:   Displays the nth character of a string. *)
fun dispnthc(s, 1) = hd(explode s)
|   dispnthc(s, n) = dispnthc(implode(tl(explode s)), n-1);

(* HW NUMBER: #2 *)
(* FUNCTION NAME: (delnthc) delete nth character *)
(* DESCRIPTION:   Deletes the nth character of a string. Returns string without character. *)
fun delnthc("",_) = ""
|   delnthc(s, 1) = implode(tl(explode s))
|   delnthc(s, n) = str(hd(explode s)) ^ delnthc (implode(tl(explode s)), n - 1);

(* HW NUMBER: #3 *)
(* FUNCTION NAME: (multin) multiple in *)
(* DESCRIPTION:   Takes a list of three [a,b,c] and multiplies a by b for c times. *)
fun multin([a,b,~1]) = nil
|   multin(a::b::c::xs) = a  :: multin([a * b, b, c - 1]);
    
(* HW NUMBER: #4 *)
(* FUNCTION NAME: (remv) remove *)
(* DESCRIPTION:   Removes all occurances of a given element within a given list. *)
fun remv(_, nil) = nil
|   remv(s, x::xs) =
    if s=x then remv(s, xs)
    else x::remv(s, xs);

(* HW NUMBER: #5 *)
(* FUNCTION NAME: (remvdub) remove dubplicate *)
(* DESCRIPTION:   Removes all duplicate occurances of an element within a given list. *)
fun remvdub(nil) = nil
|   remvdub(x::xs) = x::remvdub(remv(x, x::xs));

(* HW NUMBER: #6 *)
(* FUNCTION NAME: (int2str) integer to string *)
(* DESCRIPTION:   Converts an integer to a string. *)
fun int2str(0) = "" 
|   int2str(n) = int2str(floor(real n / 10.0)) ^ 
         str(chr(round(((real n / 10.0) - 
         real(floor(real n / 10.0))) * 10.0) + 48));

(* HW NUMBER: #7 *)
(* FUNCTION NAME: (str2intHelper) str2intHelper *)
(* DESCRIPTION:   Returns 1 or if length > 1 then 10 ^ (length of given string - 1). *)
fun str2intHelper("")= 1.0 / 10.0
|   str2intHelper(s) = 10.0 * str2intHelper(implode(tl(explode s)));

(* HW NUMBER: #7 *)
(* FUNCTION NAME: (str2int) string to integer *)
(* DESCRIPTION:   Converts an string to a integer. *)
fun str2int("") = 0
|   str2int(s) = ((ord(hd(explode s)) - 48) * round(str2intHelper(s))) +  
    str2int(implode(tl(explode s)));

(* HW NUMBER: #8 *)
(* FUNCTION NAME: (diff2Helper) diff2Helper *)
(* DESCRIPTION:    Returns true if a given element is within a given list. *)
fun diff2Helper(_, nil) = false
|   diff2Helper(a, x::xs) =
    if x = a then true
    else diff2Helper(a, xs);

(* HW NUMBER: #8 *)
(* FUNCTION NAME: (diff2) difference two *)
(* DESCRIPTION:    Returns the set difference of two given lists. *)
fun diff2(nil, L2) = nil
|   diff2(L1, nil) =  nil
|   diff2(x::xs, L2) = 
    if not(diff2Helper(x, L2)) then x::diff2(xs, L2)
    else diff2(xs, L2);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (addIndex) add index *)
(* DESCRIPTION:   Changes list to (Z * int) list. The int being the index of the element. *)
fun addIndex (i, nil) = nil
  | addIndex (i, x::xs) = (x,i) :: addIndex(i+1, xs);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (indeTuple) index tuple *)
(* DESCRIPTION:   Finds the index of a given element within a (Z * int) list. *)
fun indeTuple(_, nil) = nil
|   indeTuple(n, (a,b)::xs) = 
    if n = a then b :: indeTuple(n, xs) 
    else indeTuple(n, xs);

(* HW NUMBER: #9 *)
(* FUNCTION NAME: (inde) inde *)
(* DESCRIPTION:   Finds the index of a given element within list. *)
fun inde(n, L:int list) = indeTuple(n, addIndex(1,L));

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occrRemover) occrRemover *)
(* DESCRIPTION:   Returns the list with all of a given element removed. *)
fun occrRemover(nil, _) = nil
|   occrRemover(x::xs, n) = 
    if n = x then occrRemover(xs, n)
    else x :: occrRemover(xs, n);

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occrHelper) occrHelper *)
(* DESCRIPTION:   Returns the amount of a given element in a list. *)
fun occrHelper(nil, _) = 0
|   occrHelper(x::xs, n) = if n=x then 1 + occrHelper(xs, n) 
    else occrHelper(xs, n);

(* HW NUMBER: #10 *)
(* FUNCTION NAME: (occr) occurence *)
(* DESCRIPTION:    Displays the occurence of an element of a list. *)
fun occr(nil) = nil
|   occr(x::xs) = (x, occrHelper(x::xs, x)) :: occr(occrRemover(x::xs, x));

(* HW NUMBER: #11 *)
(* FUNCTION NAME: (neleHelper) neleHelper *)
(* DESCRIPTION:   Repeats each element in a list n times. *) 
fun neleHelper(nil, _, _) = nil
|   neleHelper(x::xs, b, c) =  
    if b=1 then x :: neleHelper(xs, c, c)
    else x :: neleHelper(x::xs, b-1, c);

(* HW NUMBER: #11 *)
(* FUNCTION NAME: (nele) n elements *)
(* DESCRIPTION:   Repeats each element in a list n times. *) 
fun nele(nil, _) = nil
|   nele(L, n) = neleHelper(L, n, n);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrinREV) ntrinREV *)
(* DESCRIPTION:   Reverses a list. *)
fun ntrinREV (nil, L2) = L2
|   ntrinREV (x::xs, L2) = ntrinREV (xs, (x)::L2);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrinHelper) ntrinHelper *)
(* DESCRIPTION:   Generates the triangular of a given number. *)
fun ntrinHelper(1) =  1
|   ntrinHelper(n) = ntrinHelper(n-1) + n;

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrin) n triangle *)
(* DESCRIPTION:   Generates a list of n triangular numbers from 1 to triangluar(n) in reverse. *)
fun ntrinL(0) = nil
|   ntrinL(n) = ntrinHelper(n) :: ntrinL(n-1);

(* HW NUMBER: #12 *)
(* FUNCTION NAME: (ntrin) n triangle *)
(* DESCRIPTION:   Generates a list of n triangular numbers from 1 to triangluar(n). *)
fun ntrin(n)= ntrinREV(ntrinL(n), nil);

(* HW NUMBER: #13 *)
(* FUNCTION NAME: (isfactHelper) isfactHelper *)
(* DESCRIPTION:   Divides n by a given x until n is 1 or something else. (factorial or not factorial) *)
fun isfactHelper(1, _) = true 
|   isfactHelper(n, x) = 
    let 
       val value = n mod x
       val next = n div x
    in
       if not(value = 0) andalso not(n=1)then false
       else isfactHelper(next, x + 1) 
    end

(* HW NUMBER: #13 *)
(* FUNCTION NAME: (isfact) is factorial *)
(* DESCRIPTION:   Returns true if the number given a is a factorial number. *)
fun isfact(n) =
    isfactHelper(n, 2); 

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliatHead) split at head *)
(* DESCRIPTION:   Returns a string from index 0 to index n. *)
fun spliatHead(s, 0) = ""
|   spliatHead(s, n) = str(hd(explode(s))) ^ spliatHead(implode(tl(explode(s))), n-1);

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliatTail) split at tail *)
(* DESCRIPTION:   Returns a string from index n to the end of the string. *)
fun spliatTail(s, 0) = s
|   spliatTail(s, n) = spliatTail(implode(tl(explode(s))), n-1);

(* HW NUMBER: #14 *)
(* FUNCTION NAME: (spliat) split at *)
(* DESCRIPTION:   Returns a list of 2 parts of a string that was split at a given position of n. *)
fun spliat(s, n) = 
    [spliatHead(s,n) , spliatTail(s,n)];

(* HW NUMBER: #15 *)
(* FUNCTION NAME: (inseachHelper) inseachHelper *)
(* DESCRIPTION:   Returns a list list with an element to each position of a list. *)
fun inseachHelper(n, L1, nil) = [L1@n::nil]
|   inseachHelper(n, L1, x::xs) = (L1 @ (n :: (x::xs))) :: inseachHelper(n, L1 @ [x] , xs);

(* HW NUMBER: #15 *)
(* FUNCTION NAME: (inseach) inseach*)
(* DESCRIPTION:   Driver for insearchHelper. *)
fun inseach(n, L) =
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

