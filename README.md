gdxenginecore
=============

java game engine built on top of libgdx


status
-------------
Still rough from a refactor, the most polished segments at this point are `Event` and `Pool`.
Static utilities are a mess, actors and graphics are a mess, and physics is on the whole crufty.

Next priority will be de-crufting physics and pulling out the common boilerplate-heavy operations,
most likely hit detection and various region queries, especially pathfinding.  Since navigation in general
is an insane pile of work and optimization, I'm hoping to cut the scope drastically by imposing very limiting
geometry and agent constraints, so that orientation et al. can be simplified down to circles and boxes.
