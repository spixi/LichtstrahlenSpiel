\begin{struktogramm}(90,160)
\assign
{
\begin{declaration}[Parameter]
\description{\(board\)}{Das Spielbrett}
\end{declaration}
}
\assign{
\begin{declaration}[globale Variablen]
\description{\(strategien\)}{Array von Strategien}
\description{\(lichtfelder\)}{Array von Lichtfeldern}
\description{\(zahlenfelder\)}{Array von Zahlenfeldern}
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
\ifthenelse{1}{2}{\(sum(lichtfelder) \ne sum(zahlenfelder)\)}{\sTrue}{\sFalse}
\return{Puzzle unl"osbar}
\change
\assign{\(i \gets 0\)}
\while{\(\lnot istGel"ost(board) \wedge i \le maxIterationen\)}
\while{\(\forall tile \in board\)}
\sub{\(step(tile,0)\)}
\assign{\(i \gets i+1\)}
\whileend
\whileend
\ifthenelse{2}{1}{\(i = maxIterationen\)}{\sTrue}{\sFalse}
\return{Max. Anz. Iterationen "uberschritten}
\change
\ifend
\ifend
\end{struktogramm}