;;; HW NUMBER: #1 
;;; FUNCTION NAME: (dispnthc) display nth character 
;;; DESCRIPTION:   Displays the nth character of a string. 
(defun dispnth (L n)
(if (= n 1) (first L)
(dispnth (rest L) (- n 1))))

;;; HW NUMBER: #2 
;;; FUNCTION NAME: (delnthc) delete nth character 
;;; DESCRIPTION:   Deletes the nth character of a string. Returns string without character. 
(defun delnth (L n)
(cond((null L) nil)
    ((= n 1) (delnth (rest L) (- n 1)))
    (t (cons (first L) (delnth (rest L) (- n 1))))))

;;; HW NUMBER: #3 
;;; FUNCTION NAME: (lastele) last element
;;; DESCRIPTION:   Returns the last occurance of a element within a list. 
(defun lastele (L)
(cond((null L) nil)
    ((null (rest L)) (first L))
    (t (lastele (rest L)))))
	
;;; HW NUMBER: #4
;;; FUNCTION NAME: (lastele2H) last element 2 helper
;;; DESCRIPTION:   Returns the last occurance of a list within a list. 
(defun lastele2H (L ele)
(cond((null L) ele)
    ((listp (first L)) (lastele2H (rest L) (first L)))
    (t (lastele2H (rest L) ele))))
	
;;; HW NUMBER: #4
;;; FUNCTION NAME: (lastele2) last element 2
;;; DESCRIPTION:   Returns the last occurance of a list within a list. 
(defun lastele2 (L)
(lastele2H L nil))

;;; HW NUMBER: #5 
;;; FUNCTION NAME: (remv) remove 
;;; DESCRIPTION:   Removes all occurances of a given element within a given list. 
(defun remv (s L)
(cond((null L) nil)
    ((eq s (first L)) (remv s (rest L)))
    (t (cons (first L) (remv s (rest L))))))
	
;;; HW NUMBER: #6 
;;; FUNCTION NAME: (remv2) remove 2
;;; DESCRIPTION:   Removes all occurances of a given list elements within a given list. 
(defun remv2 (r L)
    (cond ((null L) nil)
    ((and (listp (first L)) (equal r (first L))) (remv2 r (rest L)))
    (t (cons (first L) (remv2 r (rest L))))))
	
;;; HW NUMBER: #7 
;;; FUNCTION NAME: (remvdub) remove dubplicate 
;;; DESCRIPTION:   Removes all duplicate occurances within a given list. 
(defun remvdub (L)
(cond((null L) nil)
    (t (cons (first L) (remvdub (remv (first L) L ) )))))
	
;;; HW NUMBER: #8 
;;; FUNCTION NAME: (remvdub2) remvove duplicate 2 
;;; DESCRIPTION:   Removes all duplicate occurances within a given list even lists. 
(defun remvdub2 (L)
    (cond ((null L) nil)
    ((listp (first L)) (cons (first L) (remvdub2 (remv2 (first L) L))))
    (t (cons (first L) (remvdub2(remv (first L) L))))))
	
;;; HW NUMBER: #9 
;;; FUNCTION NAME: lists
;;; DESCRIPTION:   returns the list elements of a given list. 
(defun lists (L)
    (cond ((null L) nil)
    ((listp (first L)) (cons (first L) (lists (rest L))))
    (t (lists (rest L)))))

;;; HW NUMBER: #10
;;; FUNCTION NAME: (indeH) index helper
;;; DESCRIPTION:   Converts an integer to a string. 
(defun indeH (x L i)
    (cond ((null L) nil)
    ((= x (first L)) (cons i (indeH x (rest L) (+ i 1))))
    (t (indeH x (rest L) (+ i 1)))))

;;; HW NUMBER: #10
;;; FUNCTION NAME: (inde) index
;;; DESCRIPTION:   Converts an integer to a string. 
(defun inde (x L)
    (indeH x L 1))
	
;;; HW NUMBER: #11 
;;; FUNCTION NAME: (neleHelper) neleHelper 
;;; DESCRIPTION:   Repeats each element in a list n times.  
(defun neleH (L x i)
    (cond ((null L) nil)
    ((= i 1) (cons (first L) (neleH (rest L) x x)))
    (t (cons (first L) (neleH L x (- i 1))))))

;;; HW NUMBER: #11 
;;; FUNCTION NAME: (nele) n elements 
;;; DESCRIPTION:   Repeats each element in a list n times.  
(defun nele (L x)
    (neleH L x x))
	

;;; HW NUMBER: #12 
;;; FUNCTION NAME: (isTrinH) is triangular helper 
;;; DESCRIPTION:   Determines if a string is triangular. 
(defun isTrinH (n x)
    (cond ((= (- n x) 0) t)
    ((< (- n x) 0) nil)
     (t (isTrinH (- n x) (+ x 1)))))
	 
;;; HW NUMBER: #12
;;; FUNCTION NAME: (istrin) is triangular
;;; DESCRIPTION:   Determines if a string is triangular.  
(defun istrin (n)
    (istrinH n 1))
	
;;; HW NUMBER: #13 
;;; FUNCTION NAME: (occurH) occurence helper 
;;; DESCRIPTION:   Returns the amount of a given element in a list. 
(defun occurH (n L)
(cond((null L) 0)
    ((= n (first L)) (+ 1 (occurH n (rest L))))
    (t (occurH n (rest L)))))
    
;;; HW NUMBER: #13 
;;; FUNCTION NAME: (occr) occurence 
;;; DESCRIPTION:    Displays the occurence of an element of a list. 
(defun occur (L)
(cond((null L) nil)
    (t (cons 
        (cons (first L) (list(occurH (first L) L))) (occur (remv (first L) L)) ))))
		
;;; HW NUMBER: #13 
;;; FUNCTION NAME: (getHighOccur) get highest occurence 
;;; DESCRIPTION:    Returns the occurence number of the highest occured element. 
(defun getHighOccur(m L)
(cond((null L) m)
    ((< m (first (rest (first  L)))) (getHighOccur (first (rest (first L))) (rest L)))
    (t (getHighOccur m (rest L)))))
    
;;; HW NUMBER: #13 
;;; FUNCTION NAME:  filter 
;;; DESCRIPTION:    Filters out non-highest occurence and returns a list of highest occurence. 
(defun filter(F L)
    (cond((null L) nil)
    ((funcall F (rest (first L))) (filter F (rest L)))
    (t (cons (first L) (filter F (rest L))))))

;;; HW NUMBER: #13 
;;; FUNCTION NAME:  (model) mode list 
;;; DESCRIPTION:    Returns the mode and its occurrence. 
(defun model(L)
    (filter #'(lambda (x) (< (first x) (getHighOccur 0 (occur L)))) (occur L)))

;;; HW NUMBER: #14 
;;; FUNCTION NAME: (inseachHelper) inseachHelper 
;;; DESCRIPTION:   Returns a list list with an element to each position of a list. 
(defun inseachHelper(n L1 L2)
    (if (null L2)  (list (append L1 (list n)))
    (cons (append L1 (cons n L2)) (inseachHelper n (append L1 (list (first L2))) (rest L2)))))
 
;;; HW NUMBER: #14 
;;; FUNCTION NAME: (inseach) inseach
;;; DESCRIPTION:   Driver for insearchHelper. 
(defun inseach (n L) (inseachHelper n nil L))

;;; HW NUMBER: #14 
;;; FUNCTION NAME: (firstL) first list
;;; DESCRIPTION:   Creates a list of permutation starting from 1. 
(defun firstL(n g)
(if (= n 1) (list(list 1))
(inseach n (first(firstL (- n 1) g)))))

;;; HW NUMBER: #14 
;;; FUNCTION NAME: (restL) rest of list
;;; DESCRIPTION:   Creates a list of list of all permutations. 
(defun restL(n L)
 (if (null L) nil
 (append (first (mapcar (function inseach) (list n) L)) (restL n (rest L)))))

;;; HW NUMBER: #14 
;;; FUNCTION NAME: (permu) permutation
;;; DESCRIPTION:   Generates [1,2, ... n] permutation. 
(defun permu(n)
(if (= n 1) (list (list 1))
(restL n (firstL (- n 1) 1))))
