---
title: Why recipe as code?
copyright: All rights reserved
---

<script>
  import AboutFeatures from '../AboutFeatures.svelte';
  const brand = "Souscode";
</script>

I spent the past few years as a software engineer, but was frustrated in my home kitchen. Traditional recipes are static, ambiguous, and hard to execute and scale. I started a weekend experiment: What if I treated a recipe as a strictly-typed Kotlin script?

The experiment became <span class="text-accent font-black">{brand}</span>. It treats cooking like software engineering—providing precision, scalability, and reusability through code.

<AboutFeatures />


## Sous OS & Recipe "Apps"

Kitchen seems like a great computing platform. Souscode could be the kernel of this environment:

* **Sous OS**: A hardware-agnostic execution environment (the "Runtime") that can be embedded in your smart kitchen, translating pure logic into physical results.
* **Recipes**: These are the "Apps." They contain the pure logic and state-checks (e.g., `water.boil()`) that the OS translates into specific instructions for your unique hardware.

## Join the Ecosystem

As an open-source experiment project:

* **For Developers**: I'm open-sourcing the DSL and the runtime. Let's "type-cast" the world's ingredients together and build the "recipe as code" database.

* **For Hardware & Industry Partners**: The license is currently not for commercial use. If you are interested in this project, feel free to reach out.

Perhaps we could make our kitchen a better execution environment.
