\begin{struktogramm}(90,137)
\assign
{
\begin{declaration}[Parameter]
\description{\(board\)}{Das Spielbrett}
\end{declaration}
}
\assign{
\begin{declaration}[globale Variablen]
\description{\(strategien\)}{Array von Strategien}
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
\assign{\(i \gets 0\)}
\while{\(\lnot istGel"ost(board) \vee i \le maxIterationen\)}
\while{\(\forall tile \in board\)}
\sub{\(step(tile,0)\)}
\assign{\(i \gets i+1\)}
\whileend
\whileend
\ifthenelse{1}{1}{\(i = maxIterationen\)}{\sTrue}{\sFalse}
\return{Max. Anz. Iterationen "uberschritten}
\change
\ifend
\end{struktogramm}