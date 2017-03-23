(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.2" :scope "test"]
                  [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all]
         '[adzerk.bootlaces :refer :all])

(def +lib-version+ "10.13.0")
(def +version+ (str +lib-version+ "-2"))
(bootlaces! +version+)

(task-options!
 pom { :project     'emergentbehavior/auth0-lock
       :version     +version+
       :description "Auth0 Lock"
       :url         "https://auth0.com/docs/libraries/lock"
       :scm         { :url "https://github.com/auth0/lock" }
       :license     { "MIT" "https://github.com/auth0/lock/blob/master/LICENSE" }})

(deftask package []
  (comp
    (download :url (format "https://github.com/auth0/lock/archive/v%s.zip" +lib-version+)
      :unzip true)
    (sift :move {#"^lock.*/build/lock\.js$"      "emergentbehavior/auth0-lock/development/lock.inc.js"
                 #"^lock.*/build/lock\.min\.js$" "emergentbehavior/auth0-lock/production/lock.min.inc.js" })
    (sift :include #{#"^emergentbehavior"})
    (deps-cljs :name "emergentbehavior.auth0-lock")
    (pom)
    (jar)))
