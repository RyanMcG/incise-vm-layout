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

(defmulti contact (fn [x & _] x))
(defmethod contact :default
  [end-point handle content]
  (let [end-point (name end-point)]
    (link-to {:title (str handle " on " end-point)}
                  (str "https://" end-point ".com/" handle)
                  content)))
(defmethod contact :email
  [_ email content]
  (mail-to {:title (str "Email me at " email)}
           email content))

(defhtml contact-spec [[end-point handle classname]]
  (let [classname (or classname (str "fa-" (name end-point)))]
    [:div.contact.pure-u-1-3
     (contact end-point handle
              [:i {:class (str "fa fa-4x " classname)}])]))

(def ^:private license-url
  "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US")

(defn license-link-to
  ([attr-map content] (link-to (merge {:rel "license"} attr-map)
                               license-url
                               content))
  ([content] (license-link-to nil content)))

(def ^:private my-btc-addr "1BgW7o3GfsuNQu3eMyEV7oM58YvUZBv4VV")
(def ^:private kcc-btc-addr "13UUaGK8ZDLxjY7RYu2bKEabqjww2KDyxD")
(defhtml feeling-generous []
  [:div#feeling-generous
   [:h6 "Do you like what you read? I accept tips."]
   [:p "Send some BTC to " [:code my-btc-addr]]
   [:p.or "OR"]
   [:p "Donate to "
    (link-to "http://iccf-holland.org/" "Kibaale Children's Centre")
    " @ " [:a {:href "http://iccf-holland.org/bitcoin.html"
               :title "Trust but verify"}
           [:code kcc-btc-addr]]]])

(def analytics-code
  "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-47124253-1', 'ryanmcg.com');
  ga('send', 'pageview');")

(defpartial footer
  "A footer parital with a Creative Commons license attached."
  [{:keys [contacts author]}]
  [:footer
   [:div.content
    [:div#cc
     [:p#cc-text
      "This website and its content are licensed under the "
      (license-link-to
        "Creative Commons Attribution-ShareAlike 3.0 Unported License")
      " by " author " except where specified otherwise."]]
    [:div#contacts
     [:div.pure-g (map contact-spec contacts)]]
    [:div#donate (feeling-generous)]
    [:p#credit "This website was "
     (link-to "https://github.com/RyanMcG/incise" "incised") "."]]
   (javascript-tag analytics-code)])

(defpartial header
  "Add nav to header"
  [_ _ [header]]
  (conj header
        [:ul#main-navigation.navigation
         [:li (link-to "/bio/" "Bio")]
         [:li (link-to "/reading/" "Reading")]
         [:li (link-to "/attributions/" "Attributions")]]
        [:div.clearfix]))

(defpartial stylesheets [] ["//cdnjs.cloudflare.com/ajax/libs/pure/0.3.0/pure-min.css"
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
