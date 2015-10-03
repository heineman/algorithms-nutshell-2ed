;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; 
;;; generate data for figure 2-11 as follows:
;;;
;;;  (briefReport testfunc plus1 50 1 64)     ;; 10,000 times
;;;
;;; generate data for figure 2-12 as follows. Different since we don't
;;; want to include costs of 2^n within the timing for multiplication.
;;;
;;;  (customReport plus1 50 1 128)

;; foldl: (X Y -> Y) Y (listof X) -> Y
;; Folds an accumulating function f across the elements of lst.
(define (foldl f acc lst)
   (if (null? lst)
       acc
       (foldl f (f (car lst) acc) (cdr lst))))


;; remove-number: (listof number) number -> (listof number)
;; remove element from list, if it exists
(define (remove-number nums x)
   (if (null? nums) '()
       (if (= (car nums) x) (cdr nums)
           (cons (car nums) (remove-number (cdr nums) x)))))


;; find-max: (nonempty-listof number) -> number
;; Finds max of the nonempty list of numbers.
(define (find-max nums)
   (foldl max (car nums) (cdr nums)))


;; find-min: (nonempty-listof number) -> number
;; Finds min of the nonempty list of numbers.
(define (find-min nums)
   (foldl min (car nums) (cdr nums)))


;; sum: (listof number) -> number
;; Sums elements in nums.
(define (sum nums)
   (foldl + 0 nums))


;; average: (listof number) -> number
;; Finds average of the nonempty list of numbers.
(define (average nums)
   (exact->inexact (/ (sum nums) (length nums))))


;; square: number -> number
;; Computes the square of x.
(define (square x) (* x x))


;; sum-square-diff: number (listof number) -> number
;; helper method for standard-deviation
(define (sum-square-diff avg nums)
   (foldl (lambda (a-number total)
            (+ total (square (- a-number avg))))
          0
          nums))


;; standard-deviation: (nonempty-listof number) -> number
;; Calculates standard deviation.
(define (standard-deviation nums)
   (exact->inexact
    (sqrt (/ (sum-square-diff (average nums) nums)
             (length nums)))))


;; Finally execute the function under test on a problem size
;; result: (number -> any) -> number
;; Computes how long it takes to evaluate f on the given probSize.
(define (result f probSize)
   (let* ((start-time (current-inexact-milliseconds))
          (result (f probSize))
          (end-time (current-inexact-milliseconds)))
     (- end-time start-time)))


;; trials: (number -> any) number number -> (listof number)
;; Construct a list of trial results
(define (trials f numTrials probSize)
   (if (= numTrials 1)
       (list (result f probSize))
       (cons (result f probSize)
             (trials f (- numTrials 1) probSize))))


;; Generate an individual line of the report table for problem size
(define (smallReport f numTrials probSize)
   (let* ((results (trials f numTrials probSize))
          (reduced (remove-number
                    (remove-number results (find-min results))
                    (find-max results))))
     (display (list 'probSize: probSize
                    'numTrials: numTrials
                    (average reduced)))
     (newline)))

;; Generate a full report for specific function f by incrementing
;; one to the problem size
(define (briefReport f inc numTrials minProbSize maxProbSize)
   (if (>= minProbSize maxProbSize)
       (smallReport f numTrials minProbSize)
       (begin
         (smallReport f numTrials minProbSize)
         (briefReport f inc numTrials (inc minProbSize) maxProbSize))))

;; pi*n: number -> number
;; Computes the pi*n
(define (piTimes x) (* 3.14159 x))

;; custom trials: (number -> any) number number -> (listof number)
;; Construct a list of trial results
(define (customTrials numTrials probSize)
   (if (= numTrials 1)
       (list (result customfunc (TwoToTheN probSize)))
       (cons (result customfunc (TwoToTheN probSize))
             (customTrials (- numTrials 1) probSize))))

;; Generate custom an individual line of the report table for problem size
(define (customSmallReport numTrials probSize)
   (let* ((results (customTrials numTrials probSize))
          (reduced (remove-number
                    (remove-number results (find-min results))
                    (find-max results))))
     (display (list 'probSize: probSize
                    'numTrials: numTrials
                    (average reduced)))
     (newline)))

;; customized to pre-compute 2^n outside of the timing routine
;;
(define (customReport inc numTrials minProbSize maxProbSize)
   (if (>= minProbSize maxProbSize)
       (customSmallReport numTrials minProbSize)
       (begin
         (customSmallReport numTrials minProbSize)
         (customReport inc numTrials (inc minProbSize) maxProbSize))))

;; used for 10,000 repetitions of multiplication
(define (customfunc ttn)
   (let loop ([i 10000]
              [result 1])
     (if (= i 0)
         result
         (loop (sub1 i) (piTimes ttn)))))


;; standard doubler and plus1 functions for advancing through report
(define (double n) (* 2 n))
(define (plus1 n) (+ 1 n))


;; helper method
(define (millionplus n) (+ 1000000 n))

;; generate 10,000 iterations of function to be tested. Return 
;; value not important
(define (testfunc n)
   (let loop ([i 10000]
              [result 1])
     (if (= i 0)
         result
         (loop (sub1 i) (TwoToTheN n)))))

;; Compute TwoToTheN
(define (TwoToTheN n)
   (let loop ([i n]
              [result 1])
     (if (= i 0)
         result
         (loop (sub1 i) (* 2 result)))))

;; Sum numbers from 1..probSize
(define (largeAdd probSize)
   (let loop ([i probSize]
              [total 0])
     (if (= i 0)
         total
         (loop (sub1 i) (+ i total)))))

