\begin{struktogramm}(100,100)
\assign
{
\begin{declaration}[]
\description{\(a, b, c\)}{three variables which are to be sorted}
\description{\(tmp\)}{temporary variable for the circular swap}
\end{declaration}
}
\ifthenelse{1}{2}{\(a\le c\)}{j}{n}
\change
\assign{\(tmp\gets a\)}
\assign{\(a\gets c\)}
\assign{\(c\gets tmp\)}
\ifend
\ifthenelse{2}{1}{\(a\le b\)}{j}{n}
\ifthenelse{1}{1}{\(b\le c\)}{j}{n}
\change
\assign{\(tmp\gets c\)}
\assign{\(c\gets b\)}
\assign{\(b\gets tmp\)}
\ifend
\change
\assign{\(tmp\gets a\)}
\assign{\(a\gets b\)}
\assign{\(b\gets tmp\)}
\ifend
\end{struktogramm}