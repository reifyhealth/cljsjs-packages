# reifyhealth/slate-react

[](dependency)
```clojure
[reifyhealth/slate-react "0.10.11-0"] ;; latest release
```
[](/dependency)

This jar comes with `deps.cljs` as used by the [Foreign Libs][flibs] feature
of the ClojureScript compiler. After adding the above dependency to your project
you can require the packaged library like so:

```clojure
(ns application.core
  (:require reifyhealth.slate-react))
```

Documentation for the slate lib can be found [on its GitHub page](https://github.com/ianstormtaylor/slate)

[flibs]: https://github.com/clojure/clojurescript/wiki/Foreign-Dependencies
