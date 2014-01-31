\begin{struktogramm}(160,220)
\assign
{
\begin{declaration}[Parameter]
\description{\(board\)}{Das Spielbrett}
\end{declaration}
}
\assign{
\begin{declaration}[globale Variablen]
\description{\(strategien\)}{Menge von Strategien}
\description{\(lichtfelder\)}{Menge von Lichtfeldern}
\description{\(zahlenfelder\)}{Menge von Zahlenfeldern}
\description{\(hooks\)}{Menge von Hooks}
\end{declaration}
}
\assign{
\begin{declaration}[lokale Variablen]
\description{\(tile\)}{einzelnes Feld des Puzzles}
\description{\(i\)}{aktuelle Iteration}
\end{declaration}
}
\assign{
\begin{declaration}[lokale Konstanten]
\description{\(maxIterations\)}{Maximale Anzahl Iterationen}
\end{declaration}
}
\ifthenelse{1}{3}{\anzahl(lichtfelder) \ne \sum{}zahlenfelder\)}{\sTrue}{\sFalse}
\return{Puzzle unl"osbar}
\change
\assign{\(i \gets 0\)}
\while{\(\lnot istGel"ost(board) \wedge i \le maxIterationen\)}
\while{\(\forall tile \in board\)}
\sub{\(step(tile,0)\)}
\ifthenelse{3}{1}{Puzzle unl"osbar?}{\sTrue}{\sFalse}
\ifthenelse{1}{1}{\(hooks \ne \emptyset\)}{\sTrue}{\sFalse}
\while{\(\forall h \in hooks\)}
\sub{\(ausf"uhren(h)\)}
\whileend
\change
\return{Puzzle unl"osbar}
\ifend
\change
\ifend
\assign{\(i \gets i+1\)}
\whileend
\whileend
\ifthenelse{2}{1}{\(i = maxIterationen\)}{\sTrue}{\sFalse}
\return{Max. Anz. Iterationen "uberschritten}
\change
\ifend
\ifend
\end{struktogramm}