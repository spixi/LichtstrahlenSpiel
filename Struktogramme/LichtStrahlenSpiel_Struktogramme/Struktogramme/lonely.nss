\begin{struktogramm}(160,220)
\assign
{
\begin{declaration}[Parameter]
\description{\(feld\)}{das von der Strategie betrachtete Lichtfeld}
\end{declaration}
}
\assign
{
\begin{declaration}[lokale Variablen]
\description{\(traverser\)}{Traverser}
\description{\(aktFeld\)}{aktuelles Feld}
\description{\(richtungen\)}{Menge von Richtungen}
\end{declaration}
}
\assign{\(richtungen \gets \emptyset\)}
\while{\(\forall richtung \in \{N, E, S, W\}\)}
\assign{\(i \gets 0\)}
\assign{Setze \(traverser\) auf \(feld\)}
\until{\(\lnot randErreicht \wedge aktFeld \in \{richtung, EMPTY\}\)}
\assign{\(aktFeld \gets next(traverser, umkehren(richtung))\)}
\assign{\(i \gets i+1\)}
\ifthenelse{3}{1}{\(istZahlenfeld(aktFeld)\)}{\sTrue}{\sFalse}
\ifthenelse{2}{1}{\(verf"ugbarerRange(aktFeld) \ge i\)}{\sTrue}{\sFalse}
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