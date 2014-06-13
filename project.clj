(defproject com.ryanmcg/incise-vm-layout "0.5.0"
  :description "The base layout I use for my personal website."
  :url "https://github.com/RyanMcG/ryanmcg.github.io"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License"
            :url "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [incise-stefon "0.1.0"]
                 [incise-base-hiccup-layouts "0.3.0"]]
  :aliases {"incise" ^:pass-through-help ["run" "-m" "incise.core"]})
