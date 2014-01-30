\begin{struktogramm}(140,220)
\assign
{
\begin{declaration}[Parameter]
\description{\(feld\)}{das von der Strategie betrachtete Zahlenfeld}
\end{declaration}
}
\assign
{
\begin{declaration}[lokale Variablen]
\description{\(richtungen\)}{Menge von Richtungen}
\description{\(richtung\)}{aktuelle Richtung}
\description{\(undoManager\)}{lokaler UndoManager}
\end{declaration}
}
\assign{Registriere Hook zum R"uckg"angigmachen im Fehlerfall}
\assign{\(observerAnmelden(undoManager,holeSpielbrett(feld))\)}
\assign{\(richtungen \gets \{N, E, S, W\}\)}
\while{\(\forall richtung in richtungen\)}
\ifthenelse{1}{1}{\(benachbartes freies Feld in richtung?\)}{\sTrue}{\sFalse}
\change
\assign{\(richtungen \gets richtungen \setminus richtung\)}
\ifend
\whileend
\ifthenelse{1}{1}{\(richtungen = \emptyset\)}{\sTrue}{\sFalse}
\assign{Puzzle nicht l"osbar}
\return{Ausnahme}
\change
\assign{W"ahle eine beliebige \(richtung \in richtungen\)}
\assign{Setze den Nachbarn in Richtung \(richtung\) auf \(richtung\)}
\ifend
\end{struktogramm}