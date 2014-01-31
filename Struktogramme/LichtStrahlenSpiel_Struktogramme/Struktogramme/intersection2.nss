\begin{struktogramm}(160,200)
\assign{
\begin{declaration}[zus"atzliche lokale Variablen]
\description{\(summe\)}{Maximale Anzahl Lichtfelder, die in die Richtungen von \(m"oglichkeit\) verteilt werden k"onnen\)}
\description{\(vertRange\)}{bereits von diesem Algorithmus verteilte Leuchtkraft}
\description{\(rangeZuVert\)}{noch zu verteilende Leuchtkraft}
\description{\(andereRichtungen\)}{Umkehrung von \(m"oglichkeit\)}
\end{declaration}
}
\assign{\(summe \gets m"oglichkeiten \cdot verfRange\)}
\ifthenelse{3}{1}{\(summe < verblRange\)}{\sTrue}{\sFalse}
\assign{Es kann maximal \(summe\) Leuchtkraft auf die Richtungen verteilt werden.}
\assign{Daher muss der Rest in die anderen Richtungen verteilt werden.}
\assign{\(andereRichtungen \gets \begin{pmatrix}0 & 0 & 0 & 1\end{pmatrix} -  m"oglichkeit\)}
\assign{\(rangeZuVert \gets verblRange - summe - vertRange\)}
\assign{Verteile den \(rangeZuVert\) auf die Richtungen aus \(andereRichtungen\)}
\return{true}
\change
\ifend
\end{struktogramm}