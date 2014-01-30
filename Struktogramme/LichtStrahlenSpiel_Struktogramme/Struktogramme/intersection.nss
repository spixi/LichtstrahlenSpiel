\begin{struktogramm}(130,160)
\assign
{
\begin{declaration}[Parameter]
\description{\(zahlenfeld\)}{das von der Strategie betrachtete Zahlenfeld}
\end{declaration}
}
\assign
{
\begin{declaration}[lokale Variablen]
\description{\(richtungen\)}{Matrix aller Kombinationen der vier Himmelsrichtungen}
\description{\(verblRange\)}{verbleibende Lichtst"arke des Zahlenfeldes}
\description{\(verfRange\)}{verf"ugbare Reichweite des Zahlenfeldes in alle vier Richtungen}
\end{declaration}
}
\assign{\(richtungen \gets \begin{pmatrix}0 & 0 & 0 & 1 \\ 0 & 0 & 1 & 0 \\  \vdots & \vdots & \vdots & \vdots \end{pmatrix}\)}
\assign{\(verblRange \gets verbleibenderRange(zahlenfeld)\)}
\assign{\(verfRange \gets rangeInAlleVierRichtungen(zahlenfeld)\)}
\ifthenelse{1}{2}{\(verfRange \lt verblRange\)}{\sTrue}{\sFalse}
\assign{Puzzle nicht l"osbar}
\return{Ausnahme}
\change
\ifthenelse{1}{2}{\(verfRange \eq verblRange\)}{\sTrue}{\sFalse}
\assign{Eindeutige L"osung setzen}
\return{true}
\change
\assign{\HUGE{TODO \ldots}} % TODO!!!!!
\ifend
\ifend
\end{struktogramm}