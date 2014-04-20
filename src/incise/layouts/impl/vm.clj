(ns incise.layouts.impl.vm
  (:require (incise.layouts [utils :refer [repartial use-layout
                                           deflayout defpartial]]
                            [core :refer [register]])
            [robert.hooke :refer [add-hook]]
            (incise.layouts.impl [page :as page-layout]
                                 [base :as base-layout])
            [stefon.core :refer [link-to-asset]]
            (hiccup [def :refer :all]
                    [util :refer [to-uri]]
                    [page :refer [include-js]]
                    [element :refer :all])))

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

(defpartial stylesheets [] ["//yui.yahooapis.com/pure/0.5.0-rc-1/pure-min.css"
                            "//yui.yahooapis.com/pure/0.5.0-rc-1/grids-responsive-min.css"
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

(register [:vm] vm)
