Ejercicio 1: Filtrado y Procesamiento de Libros

Se provee un archivo con una lista de libros (books.json) y se espera que
realices las siguientes operaciones:

1. Filtra los libros con más de 400 páginas y aquellos cuyo título contenga
   "Harry".
2. Obtén los libros escritos por "J.K. Rowling".
3. Lista los títulos ordenados alfabéticamente y cuenta cuántos libros ha
   escrito cada autor.
4. Convierte publicationTimestamp a formato AAAA-MM-DD .
5. Calcula el promedio de páginas y encuentra el libro con más y menos
   páginas.
6. Añade un campo wordCount (250 palabras por página) y agrupa los libros por
   autor.
7. (Opcional) Verifica si hay autores duplicados y encuentra los libros sin
   publicationTimestamp .
8. (Opcional) Identifica los libros más recientes.
9. (Opcional) Genera un JSON con títulos y autores y exporta la lista a CSV
   ( id , title , author_name , pages ).

   El código se implementará en Java 17 o superior en un proyecto Maven,
   además se espera un código claro y eficiente.
   Prueba técnica Backend ViveLibre
   1Cada punto detallado se implementará como un método independiente. Entrega
   tu solución en un repositorio en GitHub.