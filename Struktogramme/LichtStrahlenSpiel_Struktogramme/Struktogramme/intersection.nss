\begin{struktogramm}(160,220)
\assign
{
\begin{declaration}[Parameter]
\description{\(zahlenfeld\)}{das von der Strategie betrachtete Zahlenfeld}
\end{declaration}
}
\assign
{
\begin{declaration}[lokale Variablen]
\description{\(m"oglichkeiten\)}{Matrix \text{\footnote{Zeilenaufbau: $\begin{pmatrix}N & E & S & W\end{pmatrix}$}} aller Kombinationen der vier Himmelsrichtungen, im Programm mit \(searchPath\) bezeichnet}
\description{\(m"oglichkeit\)}{ein Zeilenvektor von \(m"oglichkeiten\)}
\description{\(ausweichpfad\)}{Kombination von Himmelsrichtungen, auf die der Lichtstrahl auf jeden Fall fallen muss}
\description{\(verblRange\)}{verbleibende Lichtst"arke des Zahlenfeldes}
\description{\(verfRange\)}{verf"ugbare Reichweite des Zahlenfeldes in alle vier Richtungen (Vektor)}
\end{declaration}
}
\assign{\(richtungen \gets \begin{pmatrix}0 & 0 & 0 & 1 \\ 0 & 0 & 1 & 0 \\  \vdots & \vdots & \vdots & \vdots \end{pmatrix}\)}
\assign{\(verblRange \gets verbleibendeLichtst"arke(zahlenfeld)\)}
\assign{\(verfRange \gets maxLichtst"arkeInAlleVierRichtungen(zahlenfeld)\)}
\ifthenelse{1}{4}{\(\sum{}verfRange < verblRange\)}{\sTrue}{\sFalse}
\assign{Puzzle nicht l"osbar}
\return{Ausnahme}
\change
\ifthenelse{1}{1}{\(\sum{}verfRange = verblRange\)}{\sTrue}{\sFalse}
\assign{Eindeutige L"osung setzen}
\return{true}
\change
\while{\(\forall m"oglichkeit \in m"oglichkeiten\)}
\sub{siehe Struktogramm \ref{fig:intersection2}}
\whileend
\ifend
\ifend
\end{struktogramm}