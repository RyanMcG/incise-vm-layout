(ns incise.transformers.impl.vm-layout
  (:require (incise.transformers [layout :refer [repartial use-layout
                                                 deflayout defpartial]]
                                 [core :refer [register]])
            [robert.hooke :refer [add-hook]]
            (incise.transformers.impl [page-layout :as page-layout]
                                      [base-layout :as base-layout])
            [stefon.core :refer [link-to-asset]]
            (hiccup [element :refer :all]
                    [util :refer [to-uri]])))

(def analytics-code
  "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-47124253-1', 'ryanmcg.com');
  ga('send', 'pageview');")

(defpartial footer
  "A footer parital with a Creative Commons license attached."
  [{:keys [contacts author]}]
  [:footer
   [:div.content
    [:p#credit "This website was "
     (link-to "https://github.com/RyanMcG/incise" "incised") "."]]
   (javascript-tag analytics-code)])

(defpartial header [] "")

(defpartial stylesheets [] ["https://unpkg.com/purecss@1.0.0/build/pure-min.css"
                            "//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css"
                            (link-to-asset "vm/stylesheets/vm.css.stefon")])
(defpartial javascripts [] [(link-to-asset "vm/javascripts/vm.js.stefon")])

(deflayout vm []
  (repartial base-layout/javascripts javascripts)
  (repartial base-layout/stylesheets stylesheets)
  (repartial base-layout/head
             #(conj % [:link {:rel "icon"
                              :type "image/png"
                              :href (to-uri "/assets/images/vm.png")}]))
  (repartial base-layout/footer footer)
  (repartial page-layout/header header)
  (use-layout page-layout/page))

(register :vm-layout vm)
