# Definitions

**Definition:** A _vertex_ is a point in $R^n$.

**Definition:** An _edge_ is a set of two vertices.

Note:
1. Two vertices are the same if and only if they coincide.
2. Edges are undirected.

**Definition:** A _polygon_ is a finite set of edges such that:
* The set of its vertices (i.e. the union of its edges) is coplanar.
* Every vertex in the polygon belongs to exactly two of its edges.

**Definition:** A _compound polygon_ is a polygon that contains another non-empty polygon as a proper subset.  
A _simple polygon_ is a non-empty polygon that is not compound.

**Lemma:** If a polygon $A$ contains polygons $B$, $C$ as subsets, then:  
1. The intersection $B\cap C$ is also a polygon.  
2. The complement $A\setminus B$ is also a polygon.  
3. The asymmetric difference $B\setminus C$ is also a polygon.

**Proof:**
> The vertices $\bigcup(B\cap C)$ and $\bigcup(A\setminus B)$ lie in the plane of $\bigcup A$.  
> Let $U$ be any vertex of $B\cap C$ or $A\setminus B$.  
> Any edges of $B\cap C$ or $A\setminus B$ containing $U$ are also edges of $A$ all containing $U$, so there can be at most two distinct such edges.  
> It remains to show that there are indeed two distinct such edges.  
> (1) For any vertex $U$ in $\bigcup(B\cap C)$, say $U\in E\in B\cap C$,  
> $U$ lies in exactly one other edge $G$ of $B$ (because $B$ is a polygon), and exactly one other edge $H$ of $C$.  
> $E$, $G$, $H$ are edges of the polygon $A$, and all contain $U$, so they cannot all be distinct. So $G=H$.  
> Therefore $G \in B\cap C$.  
> (2) For any vertex $U$ in $\bigcup(A\setminus B)$, say $U\in E\in A\setminus B$,  
> $U$ lies in exactly one other edge $F$ of $A$.  
> If $F\in B$, then there would be exactly one other edge $G\in B$ containing $U$.  
> But then $E,F,G$ would be three distinct edges in $A$ containing $U$ - a contradiction.  
> Therefore $F \in A\setminus B$.  
> (3) follows from (1) and (2) because $B\setminus C = B\cap (A\setminus C)$.  

**Corollary 1:** If a polygon $A$ contains simple polygons $B$, $C$ as subsets, then $B$ and $C$ are either disjoint or identical (as sets of edges).

**Proof:**
> Suppose $B\cap C$ is non-empty.  
> $B$ contains the polygon $B\cap C$ as a subset.  
> $B\cap C$ cannot be a proper subset of $B$ because $B$ is simple. So $B\cap C = B$.  
> Similarly, $B\cap C = C$. So $B=C$.  

**Corollary 2:** Any polygon $A$ can be expressed uniquely as the disjoint union of simple polygons, which we call its _components_.

**Proof:**
> _Existence:_  
> Let $S$ be the set of all polygons that $A$ contains as subsets.  
> Let $T\subseteq S$ be the set of all simple polygons that $A$ contains as subsets.  
> The elements of $T$ are disjoint, by Corollary 1.  
> For any edge $E$ in $A$, let $S_E$ be the set of all polygons in $A$ that contain $E$.  
> $S_E$ is non-empty, because it contains $A$ itself.  
> Let $B_E = \bigcap S_E$. $B_E$ is a polygon by the Lemma.  
> We claim that $B_E$ is Simple. Indeed:  
> > $B_E$ is non-empty, because it contains $E$.  
> > If $B_E$ contains a polygon $C$ as a proper non-empty subset, then by the Lemma, $D = B_E\setminus C = B_E\cap (A\setminus C)$ is also a polygon.  
> > $D$ is also a proper non-empty subset of $B_E$, with $B_E = C \cup D$ as a disjoint union.  
> > Either $C$ or $D$ contains $E$, so belongs to $S_E$, so contains the intersection $B_E$ as a subset.  
> > This contradicts $C,D$ being proper subsets of $B_E$.  
>
> Therefore $B_E \in T$, for each edge $E$.  
> Therefore $A$ is the disjoint union $\bigcup T$.  
> _Uniqueness:_  
> Let $T$, $T'$ each be a set of disjoint simple polygons whose union is $A$.  
> For each $B$ in $T$, $B$ is non-empty, so contains at least one edge $E$.  
> $E$ occurs in $T'$, say $E\in B'\in T'$.  
> $B\cap B'$ is non-empty (containing $E$), so $B=B'$.  
> So every element of $T$ occurs in $T'$. Similarly, every element of $T'$ occurs in $T$, so $T=T'$.  

**Lemma:** A polygon $A$ has the same number of edges as vertices. This common value is called its _order_.

**Proof:**
> Each edge of $A$ contains exactly two distinct vertices of $\bigcup A$, and each vertex of $\bigcup A$ belongs to exactly two edges of $A$.  

**Definition:** A _polyhedron_ is a set of simple polygons (called its _faces_) such that:
* Its vertices (i.e. the union of its edges, being in turn the union of the union of its faces) all lie in the same 3-dimensional affine subspace.
* Every edge in the polyhedron belongs to exactly two of its faces.

Note:
1. We stipulate that a polyhedron comprise simple polygons in order to avoid ambiguity of representation if some of the faces are coplanar.

**Definition:** A _compound polyhedron_ is a polyhedron that contains another non-empty polyhedron as a proper subset.  
A _simple polyhedron_ is a non-empty polyhedron that is not compound.

Here we have defined polyhedra in terms of polygons exactly analogous to how we defined polygons in terms of edges.
Indeed, we could continue this process through the dimensions to define polytopes etc.
The lemma on intersections and complements, and its corollaries, apply here too with an identical proof.

**Lemma:** If a polyhedron $A$ contains polyhedral $B$, $C$ as subsets, then:
1. The intersection B\cap C is also a Polyhedron.
2. The complement A\B is also a Polyhedron.
3. The asymmetric difference B\C is also a Polyhedron.

**Corollary 1:** If a polyhedron $A$ contains simple polyhedra $B$, $C$ as subsets, then $B$ and $C$ are either disjoint or identical (as sets of Polygons).

**Corollary 2:** Any polyhedron $A$ can be expressed uniquely as the disjoint union of simple polyhedra, which we call its components.

There is one noteworthy generalisation we can make, motivated by Skilling's figure:

**Definition:** A _generalised-edge-polyhedron_ is a set of polygons (called its _faces_) such that:
* Every edge in the polyhedron (i.e. in the union of its faces) belongs to an even number of its faces.

Part (a) of the above Lemma no longer applies, but we have instead:

**Lemma:** If a generalised-edge-polyhedron $A$ contains generalised-edge-polyhedra $B$, $C$ as subsets, then:
1. If $B$ and $C$ are disjoint, their union $B\cup C$ is also a generalised-edge-polyhedron.
2. The complement $A\setminus B$ is also a generalised-edge-polyhedron.
3. The symmetric difference ('exclusive or') $(B\setminus C) \cup (C\setminus B)$ is also a generalised-edge-polyhedron.

**Proof:**
> We start with (3) and deduce the others as special cases.  
> (3) For any edge $E$ in $(B\setminus C) \cup (C\setminus B)$,  
> $E$ lies in an even number $2q$ of distinct faces of $B$, and in $2r$ distinct faces of $C$.  
> Say $E$ lies in $s$ distinct polygons of $B\cap C$.  g
> (Note that $B\cap C$ is a set of polygons, but not necessarily a polyhedron.)  
> Then $E$ lies in $2q-s$ distinct polygons of $B\setminus C$, and in $2r-s$ distinct polygons of $C\setminus B$.  
> Altogether, $E$ therefore lies in an even number $2(q+r-s)$ of distinct faces of $(B\setminus C) \cup (C\setminus B)$.  
> (1) follows from (3) because, if $B$ and $C$ are disjoint, then $B\cup C = (B\setminus C) \cup (C\setminus B)$.  
> (2) follows from (3) because $A\setminus B = (A\setminus B) \cup (B\setminus A)$.  

**Corollary 1:** Any generalised-edge-polyhedron $A$ can be expressed as the disjoint union of simple generalised-edge-polyhedra. (This decomposition is not necessarily unique.)

**Proof:**
> We proceed by induction on $|A|$, the number of simple polygons in $A$.  
> If $A$ is empty or simple, then it is evidently a disjoint union of simple generalised-edge-polyhedra.  
> If $A$ is compound, say it contains the simple generalised-edge polyhedron $B$ as a proper non-empty subset.  
> By the Lemma, $C=B\setminus A$ (also a proper non-empty subset) is also a simple generalised-edge polyhedron.  
> $A = B\cup C$ as a disjoint union.  
> By the induction hypothesis, both $B$ and $C$ can be expressed as the disjoint union of simple generalised-edge-polyhedra.  
> Therefore so can $A$.  


