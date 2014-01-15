\begin{struktogramm}(90,137)
\assign
{
\begin{declaration}[Parameter]
\description{\(tile\)}{einzelnes Feld des Puzzles}
\description{\(stackPntr\)}{Zeiger auf eine Strategie}
\end{declaration}
}
\assign{
\begin{declaration}[globale Variablen]
\description{\(strategien\)}{Array von Strategien}
\end{declaration}
}
\assign{
\begin{declaration}[lokale Variablen]
\description{\(aktStrat\)}{aktuelle Strategie}
\end{declaration}
}
\ifthenelse{1}{5}{\(stackPntr = strategien.length\)}{\sTrue}{\sFalse}
\change
\assign{\(aktStrat \gets strategien(stackPntr)\)}
\ifthenelse{1}{1}{\(aktStrat \in erlaubteStrategien(tile)\)}{\sTrue}{\sFalse}
\sub{\(aktStrat(tile)\)}
\ifthenelse{1}{2}{R"uckgabewert}{\sTrue}{\sFalse}
\change
\sub{\(step(tile, stackPntr+1)\)}
\ifend
\change
\sub{\(step(tile, stackPntr+1)\)}
\ifend
\ifend
\end{struktogramm}