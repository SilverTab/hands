(ns hands.core)
;;;;;;;;;;;;;; Data

; all the possible values for cards (from 2 to ace)
(def card-values (range 2 14)) ; the possible values of a card

; we interleave our values/desc and apply hash-map to it to squash it
(def card-value-descriptions
  (apply hash-map (interleave card-values ["two" "three" "four" "five" "six"
                                           "seven" "eight" "nine" "ten" "jack"
                                           "queen" "king" "ace"])))

; card value shortnames for printing purposes
(def card-value-shortnames
  (apply hash-map (interleave card-values ["2" "3" "4" "5" "6"
                                           "7" "8" "9" "10" "J"
                                           "Q" "K" "A"])))

; all the possible suites
(def card-suites [:clubs :diamonds :hearts :spades])

; suites shortnames, for pretty printing purposes
(def card-suites-shortnames
  (apply hash-map (interleave card-suites ["♣" "♦" "♥" "♠"])))

; all the possible hands, in order of low to high
(def hand-order 
  [:high-card :pair :two-pair :three-of-a-kind
  :straight :flush :full-house :four-of-a-kind
  :straight-flush :royal-flush])

; a basic record to hold a card
(defrecord Card [cardvalue cardsuite])

;;;; Functions

; function that returns a card, has 3 arities:
; 0 args       : returns a random card
; 1 arg (v)    : returns a card of value v
; 2 args (v s) : returns a card of value v and suite s
(defn make-card
  "returns a random card or one specified by v and s"
  ([] (let [card-value (rand-nth card-values)] 
       (->Card card-value (rand-nth card-suites))))
  ([v] (->Card v (rand-nth card-suites)))
  ([v s] (if (some #{s} card-suites) 
           (->Card v s)
           (throw (Exception. "Suite does not exist")))))

; test for card equality
(defn card-equal?
  "test if 2 cards are equal"
  [c1 c2]
  (and (= (:cardvalue c1) (:cardvalue c2))
       (= (:cardsuite c1) (:cardsuite c2))))

; test if a card is in a collection of cards
(defn card-in? [col c]
  "test if a card c is in col"
  (some #(card-equal? c %) col))

(defn card-print [c]
  "a cute way to print cards"
  (format "%s%s" 
          (get card-value-shortnames (:cardvalue c))
          (get card-suites-shortnames (:cardsuite c))))

(defn make-hand []
  "generate a hand of 5 distinct cards"
  (loop [h []]
    ;(println "entering with h: " h)
    (if (= (count h) 5) ; do we have enough cards?
      h
      (let [c (make-card)]
          (if-not (card-in? h c) ; is the card in our hand alredy?
            (recur (conj h c))   ; if not, add card to hand and recurse
            (recur h))))))       ; if so, do NOT add card and recurse

(defn hand-valid? [h]
  "make sure we have 5 distinct cards"
  (and (apply distinct? h) (eq (count h))))
