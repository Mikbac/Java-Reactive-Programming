# Hot vs Cold

https://projectreactor.io/docs/core/release/reference/advancedFeatures/reactor-hotCold.html

Cold publishers generate data anew for each subscription. If no subscription is created, data never gets generated.

Hot publishers, on the other hand, do not depend on any number of subscribers. They might start publishing data right
away and would continue doing so whenever a new Subscriber comes in (in which case, the subscriber would see only new
elements emitted after it subscribed). For hot publishers, something does indeed happen before you subscribe.

Hot publisher e.g. in WebFlux we can have only one publisher and multiple subscribers.
