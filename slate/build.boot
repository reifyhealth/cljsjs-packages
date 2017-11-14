(def +lib-version+ "0.30.6")
(def +version+ (str +lib-version+ "-1"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.8.2" :scope "test"]
                 [adzerk/bootlaces "0.1.13" :scope "test"]
                 [cljsjs/immutable "3.8.1-0"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all]
         '[adzerk.bootlaces :refer :all])

(bootlaces! +version+)

(task-options!
 pom  {:project     'reifyhealth/slate
       :version     +version+
       :description "A completely customizable framework for building rich text editors."
       :url         "http://slatejs.org"
       :scm         {:url "https://github.com/ianstormtaylor/slate"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (str "https://unpkg.com/slate@" +lib-version+  "/dist/slate.js")
             :checksum "22DDF37DED894C099E0C27C87E2B1D9B"
             )
   (download :url (str "https://unpkg.com/slate@" +lib-version+  "/dist/slate.min.js")
             :checksum "772AC3884200145B4B0976B3C057DF33")
   (sift :move {#"^slate.js$"
                "reifyhealth/slate/development/slate.inc.js"
                #"^slate.min.js"
                "reifyhealth/slate/production/slate.min.inc.js"})
   (sift :include #{#"^reifyhealth"})
   (deps-cljs :name "reifyhealth.slate"
              :requires ["cljsjs.react" "cljsjs.immutable"])
   (pom)
   (jar)))
