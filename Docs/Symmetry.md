# Symmetry

**Definition:** An _isometry_ of $R^n$ is a mapping on $R^n$ that preserves distances:
	$f: R^n \to R^n$ such that, for all $x,y \in R^n$, $|f(x) - f(y)| = |x-y|$

**Fact:** In $R^3$, all isometries can be composed from _rotations_, _reflections_, and _translations_. In full they are:
* _Identity_ (leave unchanged, e.g. "rotate by angle 0")
* _Rotation_ by any angle about any axis
* _Reflection_ in any plane
* _Reflection-rotation_: Any rotation, followed by a reflection in any plane perpendicular to the rotation's axis.
* _Translation_ by any amount along any given direction
* _Screw-rotation_: Any rotation, followed by a translation by any amount along the rotation's axis.
* _Glide-reflection_: Any reflection, followed by a translation by any amount in any direction parallel to the reflection's plane.

**Definition:** The _action_ of an isometry $f$ on a vertex, edge, polygon, polyhedron, (or other set-based composition of vertices) $x$:
* If $x$ is a vertex, then $f(x)$ is as already defined for $x \in R^n$.
* If $x$ is a set $S$, then define (recursively) $f(S) = \{f(x) : x \in S\}$.

**Definition:** The _symmetry group_ of a polygon or polyhedron $P$ is the set of isometries $f$ for which $f(P)=P$.

Alternatively, in the language of group theory, by symmetry group we simply mean the stabiliser of $P$ within the group of all isometries of $R^n$.
We need just one more concept from group theory at this stage:

**Definition:** A symmetry group $G$ _acts transitively_ on a set $S$ (of vertices, edges, or other set-based composition of vertices) if:
* For any $x,y \in S$, there is at least one isometry $g \in G$ such that $g(x) = y$.

This notion of acting transitively is central to our various notions of regularity, and captures the property of all elements in consideration (e.g. vertices, edges, etc.) being indistinguishable. It is stronger than any "passing self-similarity": it asserts that if we attempt to label a vertex (say), but an adversary unpeels the label and shakes our figure around (and possibly hides it in a mirror), then we have no information whatsoever about which vertex we labelled.

**Definition:** A polygon is _regular_ if its symmetry group acts transitively, both on its vertices and on its edges.

Both parts of this definition are necessary to match our prior concept of regularity, as in "regular hexagon" etc. For example, a rectangle's symmetry group acts transitively on its vertices, but not on its edges. Conversely, the symmetry group of a rhombus acts transitively on its edges, but not on its vertices.

**Lemma:** If a symmetry group $G$ acts transitively on the edges of a polygon $P$, or on the faces of a polyhedron $P$, then it acts transitively on the components of $P$.

**Proof:**
> For any components $C$, $D$ of $P$, choose an element (edge or face respectively) $E$ of $C$, and an element $F$ of $D$.  
> By hypothesis there is an isometry $g\in G$ such that $g(E) = g(F)$.  
> $g(C) = \{g(s): s\in C\}$, so $g(E)\in g(C)$. Similarly, $g(F)\in g(D)$.  
> $g(C)$ and $g(D)$ are both components of $P$, and have non-empty intersection.  
> Therefore $g(C) = g(D)$.  

We will be interested in studying different subsets of Polyhedra according to different levels of regularity:

**Definition:** A polyhedron is:
* _Uniform_ if its faces are all regular, and its symmetry group acts transitively on its vertices and on its components.
* _Semi-regular_ if its symmetry group acts transitively on its vertices, on its edges, and on its components.
* _Regular_ if its symmetry group acts transitively on its vertices, on its edges, and on its faces.

Recall that a generalised-edge-polyhedron can be decomposed into simple components, but not necessarily uniquely. However, we can still apply the above uniformity and semi-regularity definitions by requiring, for each such decomposition (separately), transitivity on that decomposition's components.

The following (non-standard) terminology will be useful to set apart the "snub" polyhedra:

**Definition:** The _embedding symmetry group_ of a face $F$ of a polyhedron $P$ is the intersection of the respective symmetry groups of $F$ (as a polygon) and $P$.

**Definition:** A face $F$ of a polyhedron $P$ is _uniformly embedded_ in $P$ if its embedding symmetry group acts transitively on the vertices of $F$.

