(ns hands.core)

;; Everything we are going to need is defined here...


; all the possible values for cards (from 2 to ace)
(def card-values (range 2 14)) ; the possible values of a card

; we interleave our values/desc and apply hash-map to it to squash it
(def card-value-descriptions
  (apply hash-map (interleave card-values ["two" "three" "four" "five" "six"
                                           "seven" "eight" "nine" "ten" "jack"
                                           "queen" "ace"])))

; all the possible suites
(def card-suites [:clubs :diamonds :hearts :spades])

; all the possible hands, in order of low to high
(def card-hand-order 
  [:high-card :pair :two-pair :three-of-a-kind
  :straight :flush :full-house :four-of-a-kind
  :straight-flush :royal-flush])

; a basic record to hold a card
(defrecord Card [cardvalue cardvaluedesc cardsuite])

; function that returns a card, has 3 arities:
; 0 args       : returns a random card
; 1 arg (v)    : returns a card of value v
; 2 args (v s) : returns a card of value v and suite s
(defn make-card
  "returns a random card or one specified by v"
  ([] (let [card-value (rand-nth card-values)] 
       (->Card card-value (get card-value-descriptions card-value) (rand-nth card-suites))))
  ([v] (->Card v (get card-value-descriptions v) (rand-nth card-suites)))
  ([v s] (if (some #{s} card-suites) 
           (->Card v (get card-value-descriptions v) s)
           (throw (Exception. "Suite does not exist")))))

; test for card equality
(defn card-equal?
  [c1 c2]
  (and (= (:cardvalue c1) (:cardvalue c2))
       (= (:cardsuite c1) (:cardsuite c2))))
(defn card-in?
  [col c]
  (some #(card-equal? c %) col))