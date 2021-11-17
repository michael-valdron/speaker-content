(defproject leetcode "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns leetcode.core}
  :profiles {:kadanes {:repl-options {:init-ns leetcode.kadanes}}
             :inverse-tree {:repl-options {:init-ns leetcode.inverse-tree}}
             :two-sum {:repl-options {:init-ns leetcode.two-sum}}
             :power-of-two {:repl-options {:init-ns leetcode.power-of-two}}})
