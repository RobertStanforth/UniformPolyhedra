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
* If $x$ is a set $S$, then define (recursively) $f(S) = \\{f(x) : x \in S\\}$.

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
> $g(C) = \\{g(s): s\in C\\}$, so $g(E)\in g(C)$. Similarly, $g(F)\in g(D)$.  
> $g(C)$ and $g(D)$ are both components of $P$, and have non-empty intersection.  
> Therefore $g(C) = g(D)$.  

We will be interested in studying different subsets of Polyhedra according to different levels of regularity:

**Definition:** A polyhedron is:
* _Uniform_ if its faces are all regular, and its symmetry group acts transitively on its vertices and on its components.
* _Semi-regular_ if its faces are all regular, and its symmetry group acts transitively on its vertices, on its edges, and on its components.
* _Regular_ if its faces are all regular, and its symmetry group acts transitively on its vertices, on its edges, and on its faces.

Recall that a generalised-edge-polyhedron can be decomposed into simple components, but not necessarily uniquely. However, we can still apply the above uniformity and semi-regularity definitions by requiring, for each such decomposition (separately), transitivity on that decomposition's components.

The following (non-standard) terminology will be useful to set apart the "snub" polyhedra:

**Definition:** The _embedding symmetry group_ of a face $F$ of a polyhedron $P$ is the intersection of the respective symmetry groups of $F$ (as a polygon) and $P$.

**Definition:** A face $F$ of a polyhedron $P$ is _uniformly embedded_ in $P$ if its embedding symmetry group acts transitively on the vertices of $F$.

**Lemma:** The set $S=\bigcup P$ of vertices a regular polygon $P$ lie on a circle, which we call the Circumcircle of $P$.
All isometries in the symmetry group of $P$ map the circumcircle into itself, and in particular fix its Circumcentre $G$.

**Proof:**
> Introduce the centroid $G = (1/|S|) \sum_{V\in S} V$.  
> For any isometry $g$ in the symmetry group of $P$, $g$ acts on $S$ by permuting the vertices; $S$ itself is unchanged.  
> Moreover, $g(G) = g((1/|S|) \sum_{V\in S} V) = (1/|S|) \sum_{V\in S} g(V)$  (a known fact about how isometries act on convex combinations)  
> $= (1/|S|) \sum_{V\in S} V = G$.  
> For any two vertices $U,V$, let $g$ be an isometry of $P$ with $g(U)=V$. There is at least one because the symmetry group acts transitively on $S$.  
> $|U-G| = |g(U)-g(G)| = |V-G|$  
> Therefore all vertices of $P$ are equidistant from $G$.  

**Lemma:** The set $S=\bigcup\bigcup P$ of vertices of a uniform polyhedron $P$ lie on a sphere, which we call the Circumsphere of $P$.
All isometries of the symmetry group of $P$ map the circumsphere into itself, and in particular fix its Circumcentre $G$.

**Proof:** The same argument as the previous lemma applies, because we only use transitivity on the Vertices.

**Lemma:** All edges of a uniform polyhedron have the same length.

**Proof:**
> Let $E$ be an edge of uniform polyhedron $P$.  
> Let $Q$ be the subset of $P$ consisting of those faces whose edges all have the same length as $E$.  
> We claim that $Q$ is a polyhedron. Indeed:  
> > For edge $D$ in $\bigcup Q$, say $D\in F\in Q$, there is exactly one other face $G\in P$ such that $D\in G$.  
> > All edges of $G$ have the same length, because $G$ is regular.  
> > Therefore $G\in Q$.  
> > Conversely, any other face $H\in Q$ containing $D$ is itself a face in $P$ containing $Q$. So $F,G,H$ cannot all be distinct.  
> > Therefore, there are exactly two faces in $Q$ containing $D$.  
>
> Therefore, the entire component of $P$ that contains $E$ has edges all the same length.  
> The symmetry group acts transitively on the components, so all edges across all components of $P$ have the same length.  

**Lemma:** No uniform polyhedron has two distinct coplanar faces containing the same edge.

**Proof:**
> Let $P$ be a uniform polyhedron with an edge $E$ belonging to two coplanar faces $F$, $G$.
> $F$, $G$ share the same circumcircle, being the intersection of their common plane with the circumsphere of $P$.
> Let $O$ be their common circumcentre.
> We claim $F\cap G$ is a polygon. Indeed:
> > $F\cap G$ is coplanar because $F$ and $G$ are.
> > Suppose $V$ is a vertex of $F\cap G$ contained in only one edge $D=\\{U,V\\}$.
> > Let $\\{V,W\\}$ and $\\{V,X\\}$ be the unique other edge of $F$ and $G$ respectively containing $V$.
> > $U,W,X$ all lie on the same circumcircle (that of $F,G$), and are equidistant from $V$. There can only be two such points, so $W=X$.
> > Hence $\\{V,W\\}=\\{V,X\\}\in F\cap G$, contradicting choice of $V$.
> $F\cap G$ is non-empty because it contains $E$, and is a subset of the simple polygons $F$ and $G$, so equals both $F$ and $G$.
>
> Hence $F=G$.

Note:
1. This "rigidity" kind of argument will be used many times in what follows. It captures the idea of there being only one way in which an arrangement can unfold from a given starting point. It avoids having to define the actual _process_, though, by jumping straight to an analysis of the end result.

**Definition:** The Vertex Figure $VF(P)$ of a uniform polyhedron $P$ is a polygon constructed as follows:
> For a vertex $V$ of $P$, let $S = \\{W: \\{V, W\\} \in \bigcup P\\}$ be the set of vertices directly connected to $V$ by an edge of $P$.  
> All vertices in $S$ are equidistant from $V$, and also equidistant from the circumcentre of $P$, so lie in a circle.  
> Define $VF(P) = \\{\\{U, W\\} : (\exists F\in P) \\{\\{U, V\\}, \\{V, W\\}\\}\subseteq F\\}$. In other words, its edges are those $\\{U, W\\}$ for which both $\\{U, V\\}$ and $\\{V, W\\}$ belong to the same face of $P$. (Note that $\\{U, W\\}$ itself need not be an edge of $P$, and will only be so if the corresponding face of $P$ happens to be a triangle.)  
> We claim than $VF(P)$ is a polygon. Indeed:  
> > All elements $W$ in $S$ belong to $VF(P)$, by considering one of the faces of $P$ containing edge $\\{V, W\\}$.  
> > The elements of $VF(P)$ are coplanar, lying as they do in a circle.  
> > Edges $\\{U, W\\}$ of $VF(P)$ are in one-to-one correspondence with faces of $P$ containing $V$.  
> > Two such elements intersect, say $\\{U, W\\}$ and $\\{U, X\\}$, if and only if their corresponding Faces share an edge ($\\{V, U\\}$).  
>
> By transitivity over the vertices, we obtain the same vertex figure (up to congruence) regardless of which vertex $V$ we originally chose.  


