(* NAME: Khalob Cognata *)
(* FILE: Assignment Two *)
(* DATE:    3/7/2016    *)

(************* HW NUMBER: #1 **************)

(* FUNCTION NAME: isContiguous *)
(* DESCRIPTION:   Boolean function that checks if a given string is a contigous with another given string. *)
fun isContiguous(nil, L2) = true
|   isContiguous(x::xs, y::ys) =
    if x = y then isContiguous(xs, ys)
    else false;

(* FUNCTION NAME: hasSub *)
(* DESCRIPTION:   Boolean function that checks if a given string is a substring of another given string. *)
fun hasSub(s1, s2) =
    if s2="" then false
    else if isContiguous(explode(s1), explode(s2)) then true
    else hasSub(s1, implode(tl(explode(s2))));

(* FUNCTION NAME: countSub *)
(* DESCRIPTION:   Returns the index of a substring. *)
fun countSub(s1, s2) =
    if s2="" then 0
    else if isContiguous(explode(s1), explode(s2)) then 0
    else countSub(s1, implode(tl(explode(s2)))) + 1;

(* FUNCTION NAME: Substring *)
(* DESCRIPTION:   Returns the index of a given string within another given string. Case 
sensitve and will return -1 if the first string is not a substring of the second. *)
fun subString(s1, s2) =
    if hasSub(s1, s2) then countSub(s1, s2)
    else ~1;

(************* HW NUMBER: #2 **************)

(* FUNCTION NAME: (revL) reverse list*)
(* DESCRIPTION:   Reverses a list when L2 = nil. *)
fun revL(L1, L2) =
    if null(L1) then L2
    else revL(tl L1, (hd L1)::L2);

(* FUNCTION NAME: (remv) remove *)
(* DESCRIPTION:   Removes all occurances of a given element within a given list. *)
fun remv(s, L) =
    if null(L) then nil
    else if (s = hd L) then remv(s, tl L)
    else hd(L) :: remv(s, tl L);

(* FUNCTION NAME: (occrAmount) occrAmount *)
(* DESCRIPTION:   Returns the amount of a given element in a list. *)
fun occrAmount(L, n) = 
    if null(L) then 0
    else if (n = hd L) then 1 + occrAmount(tl L, n)
    else occrAmount(tl L, n);

(* FUNCTION NAME: (maxOcc) maximum occurence*)
(* DESCRIPTION:   Returns the element that occurs most. *)
fun maxOcc(L) =
    if remv(hd L, L) = nil then hd L
    else if occrAmount(L, hd(L)) > occrAmount(L, hd(remv(hd(L), L))) then maxOcc(remv(hd(remv(hd L, L)), L))
    else maxOcc(remv(hd L , L));

(* FUNCTION NAME: (modeL2) modeL2 *)
(* DESCRIPTION:   Returns a list of tuples that are (maxOccur, occurence) *)
fun modeL2(L1, L2) = 
    if null(L2) then nil 
    else if occrAmount(L1, maxOcc(L1)) = occrAmount(L2, maxOcc(L2)) 
    then (maxOcc(L2) , occrAmount(L2, maxOcc(L2))) :: modeL2(L1, remv(maxOcc(L2) ,L2))
    else nil;

(* FUNCTION NAME: Mode *)
(* DESCRIPTION:   Returns a list of tuples that are (maxOccur, occurence) *)
fun modeL(L) = 
    revL(modeL2(L, L), nil);

(************* HW NUMBER: #3 **************)

(* FUNCTION NAME: (inseachHelper) inseachHelper *)
(* DESCRIPTION:   Returns a list list with an element to each position of a list. *)
fun inseachHelper(n, L1, L2:int list) =
    if null L2 then [L1@n::L2]
    else (L1 @ (n :: L2)) :: inseachHelper(n, L1 @ [hd L2] , tl L2);

(* FUNCTION NAME: (inseach) inseach*)
(* DESCRIPTION:   Driver for insearchHelper. *)
fun inseach(n, L) = inseachHelper(n, [], L);

(* FUNCTION NAME: (restL) rest of list*)
(* DESCRIPTION:   Creates a list of list of all permutations. *)
fun restL(n, L, P) =
   if null (L) then nil
   else P(n, hd(L)) @ restL(n, tl L, P);

(* FUNCTION NAME: (firstL) first list*)
(* DESCRIPTION:   Creates a list of permutation starting from 1. *)
fun firstL(n, g, P) =
   if n = 1 then [[1]]
   else P(n, hd(firstL(n-1, g, P)));

(* FUNCTION NAME: (permu) permutation*)
(* DESCRIPTION:   Generates [1,2, ... n] permutation. *)
fun permu(n) =  restL(n, firstL(n-1, 1, inseach), inseach);
    

(************* HW NUMBER: #4 **************)

(* FUNCTION NAME: (findMin) find minimum *)
(* DESCRIPTION:   Returns the minimum element within a list. *)
fun findMin(nil) = nil
|   findMin([a]) = [a]
|   findMin(a::b::cs) = 
    if a < b then findMin(a::cs)
    else findMin(b::cs);

(* FUNCTION NAME: (removeF) remove first *)
(* DESCRIPTION:   Returns the list with the first occurence of a given element removed. *)
fun removeF(L, n, isFirst) = 
    if null(L) then nil
    else if (n = hd L) andalso isFirst then removeF(tl L, n, false)
    else hd L :: removeF(tl L, n, isFirst);

(* FUNCTION NAME: (sshelper) selection sort helper *)
(* DESCRIPTION:   Creates the sorted list, but in largest to smallest order. *)
fun sshelper(L1, nil) = L1
|   sshelper(L1, L2) =
    sshelper(hd(findMin(L2))::L1, removeF(L2,hd(findMin(L2)), true));

(* FUNCTION NAME: (ssort) selection sort *)
(* DESCRIPTION:   Drives the sshelper and reverses it. *)
fun ssort(L) = revL(sshelper(nil, L), nil);

(************* HW NUMBER: #5 **************)

(* FUNCTION NAME: (isPrime) is Prime? *)
(* DESCRIPTION:   Returns true if the number is a prime number. *)
fun isPrime(n, x) =
    if x=0 then false
    else if x=1 then true
    else if (n mod x) = 0 then false
    else isPrime(n, x-1);

(* FUNCTION NAME: (listNums) list numbers *)
(* DESCRIPTION:   Returns a list of numbers [2,3, .. n] *)
fun listNums(n, x) =
    if x = n then [n]
    else x::listNums(n, x+1);

(* FUNCTION NAME: (filter) filter *)
(* DESCRIPTION:   Returns list of numbers that are filtered to be only primes. *)
fun filter(P, nil) = nil
|   filter(P, x::xs) =
    if P(x) then x::filter(P,xs)
    else filter(P, xs);

(* FUNCTION NAME: (plist) plist*)
(* DESCRIPTION:   Drivers the filter to return a list of prime numbers from [2,3,5 ... n]. *)
fun plist(n) = filter( fn n => isPrime(n,n-1) , listNums(n, 1));


