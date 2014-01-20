\begin{struktogramm}(130,145)
\assign{\(richtungen \gets \emptyset\)}
\while{\(\forall richtung \in \{N, E, S, W\}\)}
\assign{\(i \gets 0\)}
\assign{Setze Traverser auf \(feld\)}
\until{\(\lnot randErreicht \wedge aktFeld \in \{richtung, EMPTY\}\)}
\assign{\(aktFeld \gets Traverser.next(umkehren(richtung))\)}
\assign{\(i \gets i+1\)}
\ifthenelse{3}{1}{\(istZahlenfeld(aktFeld)\)}{j}{n}
\ifthenelse{2}{1}{\(verf"ugbarerRange(akt) \ge i\)}{j}{n}
\assign{\(richtungen \gets richtungen \cup richtung\)}
\change
\ifend
\change
\ifend
\untilend
\whileend
\case{5}{3}{\(anz(richtungen)\)}{\(= 0\)}
\assign{Puzzle nicht l"osbar}
\return{Ausnahme}
\switch{\(= 1\)}
\sub{eindeutige L"osung anwenden}
\return{true}
\switch{\(\ge 1\)}
\assign{nicht eindeutig l"osbar}
\return{false}
\caseend

\end{struktogramm}