/* HW NUMBER: #1 */
/* FUNCTION NAME: (dispnthc) display nth character */
/* DESCRIPTION:   Displays the nth character of a string. */
dispnth([H|T], 1, H).
dispnth([H|T], N, Ans) :- NMod is N-1, dispnth(T, NMod, Ans).

/* HW NUMBER: #2 */
/* FUNCTION NAME: (delnthc) delete nth character */
/* DESCRIPTION:   Deletes the nth character of a string. Returns string without character. */
delnth([], _, []).
delnth([H|T], 1, T).
delnth([H|T], N, Ans) :- NMod is N-1, delnth(T, NMod, Ans2), Ans = [H | Ans2].

/* HW NUMBER: #3 */
/* FUNCTION NAME: (remv) remove */
/* DESCRIPTION:   Removes all occurances of a given element within a given list. */	
remv(_, [], []).
remv(S, [H|T], Ans):- S=H, remv(S, T, Ans).
remv(S, [H|T], Ans):- remv(S, T, Tmp), Ans = [H|Tmp].

/* HW NUMBER: #4 */
/* FUNCTION NAME: (remvdub) remove dubplicate */
/* DESCRIPTION:   Removes all duplicate occurances of an element within a given list. */
remvdub([],[]).
remvdub([H|T], Ans):- remv(H, [H|T], Tmp1), remvdub(Tmp1, Tmp2), Ans = [H|Tmp2].

/* HW NUMBER: #5 */
/* FUNCTION NAME: (maxlH) max of list helper */
/* DESCRIPTION:   Returns the highest valued number in a list. */
maxlH([], Max, Max).
maxlH([H|T], Max, Ans):- H > Max, maxlH(T, H, Ans).
maxlH([H|T], Max, Ans):- maxlH(T, Max, Ans).

/* HW NUMBER: #5 */
/* FUNCTION NAME: (maxl) max of list */
/* DESCRIPTION:   Returns the highest valued number in a list. */
maxl(L, Ans):- maxlH(L, 0, Ans).

/* HW NUMBER: #6 */
/* FUNCTION NAME: (oddthsH) odd elements helper */
/* DESCRIPTION:   Return a list of only elements at odd indexs of the given list. */
oddthsH([], _,[]).
oddthsH([H|T], 0, Ans):- oddthsH(T, 1, Tmp), Ans = [H|Tmp].
oddthsH([H|T], 1, Ans):- oddthsH(T, 0, Tmp), Ans = Tmp.

/* HW NUMBER: #6 */
/* FUNCTION NAME: (oddths) odd elements */
/* DESCRIPTION:   Return a list of only elements at odd indexs of the given list. */
oddths(L, Ans):- oddthsH(L, 0, Ans).

/* HW NUMBER: #7 */
/* FUNCTION NAME: (suml) sum of list */
/* DESCRIPTION:   Returns the sum of all numbers in a list. */
suml([],0).
suml([H|T], Ans):- is_list(H), suml(H, Ans1), suml(T, Ans2), Ans is Ans1 + Ans2.
suml([H|T], Ans):- suml(T, Ans1), Ans is Ans1 + H.

/* HW NUMBER: #8 */
/* FUNCTION NAME: (addIndex) add index */
/* DESCRIPTION:   Changes list to (Z * int) list. The int being the index of the element. */
addIndex(_, [], []).
addIndex(I, [H|T], Ans):- Tmp is I+1, addIndex(Tmp, T, Tmp2), Ans = [[H|I]|Tmp2]. 

/* HW NUMBER: #8 */
/* FUNCTION NAME: (indeTuple) index tuple */
/* DESCRIPTION:   Finds the index of a given element within a (Z * int) list. */
indeTuple(_,[],[]).
indeTuple(N,[[A,B|C]|T], Ans):- N = A, indeTuple(N,T, Tmp), Ans = [B|Tmp].
indeTuple(N,[[A,B|C]|T], Ans):- indeTuple(N,T, Ans).

/* HW NUMBER: #8 */
/* FUNCTION NAME: (inde) inde */
/* DESCRIPTION:   Finds the index of a given element within list. */
inde(N, L, Ans):- addIndex(1, L, Tmp), indeTuple(N, Tmp, Ans).

/* HW NUMBER: #9 */
/* FUNCTION NAME: (neleHelper) neleHelper */
/* DESCRIPTION:   Repeats each element in a list n times. */ 
neleHelper([], _, _, []).
neleHelper([H|T], B, C, Ans):- B=1, neleHelper(T, C, C, Tmp), Ans = [H|Tmp].
neleHelper([H|T], B, C, Ans):- BMod is B-1, neleHelper([H|T], BMod, C, Tmp), Ans = [H|Tmp].

/* HW NUMBER: #9 */
/* FUNCTION NAME: (nele) n elements */
/* DESCRIPTION:   Repeats each element in a list n times. */ 
nele([], _, []).
nele(L, N, Ans):- neleHelper(L, N, N, Ans).
