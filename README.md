
# cljc-fruits

### Overview

The <strong>cljc-fruits</strong> is a set of at least 500 Clojure/ClojureScript
utility functions which helps you to develop fast and easy. For example, by using
this library, you don't have to thinking about how to move an item in a vector
to a specific position or how to find the 42th occurrence of an expression in a string.

### deps.edn

```
{:deps {bithandshake/cljc-fruits {:git/url "https://github.com/bithandshake/cljc-fruits"
                                  :sha     "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"}}
```

### Current version

Check out the latest commit on the [release branch](https://github.com/bithandshake/cljc-fruits/tree/release).

### Documentation

The <strong>cljc-fruits</strong> functional documentation is [available here](documentation/COVER.md).

### Changelog

You can track the changes of the <strong>cljc-fruits</strong> library [here](CHANGES.md).

# What's in this library?

- Base64 encoding functions in the [`base64.api`](documentation/cljc/base64/API.md) namespace.

- Blob object functions in the [`blob.api`](documentation/cljc/blob/API.md) namespace.

- Simple noop functions in the [`candy.api`](documentation/cljc/candy/API.md) namespace.

- Source code evaluator functions in the [`code.api`](documentation/cljc/code/API.md) namespace.

- CSS parser, unparser and wrapper functions in the [`css.api`](documentation/cljc/css/API.md) namespace.

- EQL syntax functions in the [`eql.api`](documentation/cljc/eql/API.md) namespace.

- Isomorphic error handler functions in the [`error.api`](documentation/cljc/error/API.md) namespace.

- Number formatting functions in the [`format.api`](documentation/cljc/format/API.md) namespace.
