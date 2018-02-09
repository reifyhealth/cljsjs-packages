(def +lib-version+ "8.0.6")
(def +version+ (str +lib-version+ "-0"))

(set-env!
 :resource-paths #{"resources"}
 :dependencies '[[cljsjs/boot-cljsjs "0.9.0" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(task-options!
 pom  {:project     'cljsjs/jsrsasign
       :version     +version+
       :description "An open-source, free, pure JavaScript cryptographic library which supports RSA/RSAPSS/ECDSA/DSA signing/validation, ASN.1, PKCS#1/5/8 private/public key, X.509 certificate, CRL, CMS SignedData, TimeStamp and CAdES and JSON Web Signature(JWS)/Token(JWT)/Key(JWK)."
       :url         "https://kjur.github.io/jsrsasign"
       :scm         {:url "https://github.com/kjur/jsrsasign"}
       :license     {"MIT" "http://opensource.org/licenses/MIT"}})

(deftask package  []
  (comp
   (download :url (str "https://unpkg.com/jsrsasign@" +lib-version+ "/lib/jsrsasign.js"))
   (download :url (str "https://unpkg.com/jsrsasign@" +lib-version+ "/lib/jsrsasign-all-min.js"))
   (sift :move {#"^jsrsasign.js$"        "cljsjs/jsrsasign/development/jsrsasign.inc.js"
                #"^jsrsasign-all-min.js" "cljsjs/jsrsasign/production/jsrsasign.min.inc.js"})
   (sift :include #{#"^cljsjs"})
   (deps-cljs :name "cljsjs.jsrsasign")
   (pom)
   (jar)
   (validate-checksums)))
