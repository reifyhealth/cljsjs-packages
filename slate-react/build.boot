(def +lib-version+ "0.10.11")
(def +version+ (str +lib-version+ "-0"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.8.2" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all]
         '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!
 pom  {:project     'reifyhealth/slate-react
       :version     +version+
       :description "A completely customizable framework for building rich text editors."
       :url         "http://slatejs.org"
       :scm         {:url "https://github.com/ianstormtaylor/slate"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (str "https://unpkg.com/slate-react@" +lib-version+  "/dist/slate-react.js")
             :checksum "6C5E13A4311995A1B72EF8643C6AB0F8")
   (download :url (str "https://unpkg.com/slate-react@" +lib-version+  "/dist/slate-react.min.js")
             :checksum "C9301C8EF5D4DB3EAA5A6F7049EAB82F")
   (sift :move {#"^slate-react.js$"
                "reifyhealth/slate-react/development/slate-react.inc.js"
                #"^slate-react.min.js"
                "reifyhealth/slate-react/production/slate-react.min.inc.js"})
   (sift :include #{#"^reifyhealth"})
   (deps-cljs :name "reifyhealth.slate-react"
              :requires ["cljsjs.react" "cljsjs.react-dom" "reifyhealth.slate"])
   (pom)
   (jar)))
