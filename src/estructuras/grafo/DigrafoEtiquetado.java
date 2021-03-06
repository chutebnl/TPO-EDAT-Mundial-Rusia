package estructuras.grafo;

import estructuras.lineales.Cola;
import estructuras.lineales.ColaDinamica;
import estructuras.lineales.Lista;
import estructuras.lineales.ListaDinamica;

import java.io.Serializable;

public class DigrafoEtiquetado<E> implements Grafo<E>, Serializable {
    protected NodoVert<E> inicio;

    public DigrafoEtiquetado() {
        this.inicio = null;
    }

    @Override
    public boolean insertarVertice(E elem) {
        boolean existeVert = exiteVertice(elem);
        if (!existeVert) inicio = new NodoVert<>(elem, inicio);
        return !existeVert;
    }

    /**
     * Busca un nodo vértice con el elemento enviado por parámetro.
     *
     * @param elem el elemento a buscar
     * @return el nodo que contiene el elemento
     */
    protected NodoVert<E> buscarVertice(E elem) {
        NodoVert<E> nodoVert = inicio;
        if (elem != null) {
            while (nodoVert != null && !nodoVert.getElem().equals(elem))
                nodoVert = nodoVert.getSigVertice();
        }
        return nodoVert;
    }

    /**
     * Busca el primer vertice que encuentra de ambos parámetros
     *
     * @param elem1 elemento a buscar 1
     * @param elem2 elemento a buscar 2
     * @return un nodo vertice
     */
    protected NodoVert<E> buscarPrimerVertice(E elem1, E elem2) {
        if (elem1 != null && elem2 != null) {
            NodoVert<E> nodoVert = inicio;
            while (nodoVert != null && !nodoVert.getElem().equals(elem1) && !nodoVert.getElem().equals(elem2))
                nodoVert = nodoVert.getSigVertice();
            return nodoVert;

        }
        return null;
    }

    /**
     * Busca ambos vertices y los devuelve en un arreglo con las posiciones despectivas de los parametros
     *
     * @param elem1 elemento a buscar 1
     * @param elem2 elemento a buscar 2
     * @return un arrelo con [verticeElem1, verticeElem2]
     */
    protected NodoVert<E>[] buscarDosVertices(E elem1, E elem2) {
        NodoVert<E>[] vertices = new NodoVert[2];

        if (elem1 != null && elem2 != null) {
            NodoVert<E> nodoI = inicio; // itera sobre la lista de vertices

            while (nodoI != null && (vertices[0] == null || vertices[1] == null)) {
                if (nodoI.getElem().equals(elem1))
                    vertices[0] = nodoI;
                if (nodoI.getElem().equals(elem2))
                    vertices[1] = nodoI;

                nodoI = nodoI.getSigVertice();
            }
        }

        return vertices;
    }

    protected NodoVert<E>[] buscarTresVertices(E elem1, E elem2, E elem3) {
        NodoVert<E>[] vertices = new NodoVert[3];

        if (elem1 != null && elem2 != null) {
            NodoVert<E> nodoI = inicio; // itera sobre la lista de vertices

            while (nodoI != null && (vertices[0] == null || vertices[1] == null || vertices[2] == null)) {
                if (nodoI.getElem().equals(elem1))
                    vertices[0] = nodoI;
                if (nodoI.getElem().equals(elem2))
                    vertices[1] = nodoI;
                if (nodoI.getElem().equals(elem3))
                    vertices[2] = nodoI;

                nodoI = nodoI.getSigVertice();
            }
        }

        return vertices;
    }

    public E obtenerVertice(E elem) {
        E resp = null;
        NodoVert<E> vert = buscarVertice(elem);
        if (vert != null)
            resp = vert.getElem();
        return resp;
    }

    @Override
    public boolean eliminarVertice(E elem) {
        boolean elimino = false;
        NodoVert<E> vert, vertAnt;

        if (elem != null && inicio != null) {
            if (inicio.getElem().equals(elem)) {
                inicio = inicio.getSigVertice();
                elimino = true;
            } else {
                vert = inicio.getSigVertice();
                vertAnt = inicio;
                while (!elimino && vert != null) {
                    if (vert.getElem().equals(elem)) {
                        elimino = true;
                        vertAnt.setSigVertice(vert.getSigVertice());
                    } else {
                        vertAnt = vert;
                        vert = vert.getSigVertice();
                    }
                }
            }
            if (elimino) eliminarAdyacentes(elem);
        }

        return elimino;
    }

    private void eliminarAdyacentes(E vertAEliminar) {
        NodoVert<E> vertice = inicio;
        while (vertice != null) {
            eliminarArco(vertice, vertAEliminar);
            vertice = vertice.getSigVertice();
        }
    }

    @Override
    public boolean insertarArco(E origen, E destino) {
        return insertarArco(origen, destino, 1);
    }

    @Override
    public boolean insertarArco(E origen, E destino, int etiqueta) {
        boolean inserto = false;
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> nodoOrigen = vertices[0];
        NodoVert<E> nodoDestino = vertices[1];

        if (nodoOrigen != null && nodoDestino != null) {
            if (!existeArco(nodoOrigen, nodoDestino)) {
                nodoOrigen.setPrimerAdy(new NodoAdy<>(nodoDestino, nodoOrigen.getPrimerAdy(), etiqueta));
                inserto = true;
            }
        }

        return inserto;
    }

    @Override
    public boolean eliminarArco(E origen, E destino) {
        boolean elimino = false;
        if (origen != null && destino != null)
            elimino = eliminarArco(buscarVertice(origen), destino) != null;
        return elimino;
    }

    /**
     * Quita el arco de desde el vertice origen al destino y retorna el vertice hacia donde apunta el arco eliminado.
     *
     * @param vertOrigen vertice origen del arco
     * @param destino    elemento destino del arco
     * @return el vertice hacia donde apunta el arco eliminado
     */
    protected NodoVert<E> eliminarArco(NodoVert<E> vertOrigen, E destino) {
        NodoAdy<E> ady, adyAnt;
        NodoVert<E> nodoVertEliminado = null;

        if (vertOrigen != null && vertOrigen.getPrimerAdy() != null) {
            if (vertOrigen.getPrimerAdy().getVertice().getElem().equals(destino)) {
                nodoVertEliminado = vertOrigen.getPrimerAdy().getVertice();
                vertOrigen.setPrimerAdy(vertOrigen.getPrimerAdy().getSigAdy());
            } else {
                adyAnt = vertOrigen.getPrimerAdy();  // adyacente anterior a 'ady'
                ady = vertOrigen.getPrimerAdy().getSigAdy();

                while (nodoVertEliminado == null && ady != null) {
                    if (ady.getVertice().getElem().equals(destino)) {
                        nodoVertEliminado = ady.getVertice();
                        adyAnt.setSigAdy(ady.getSigAdy());
                    } else {
                        adyAnt = ady;
                        ady = ady.getSigAdy();
                    }
                }
            }
        }
        return nodoVertEliminado;
    }

    @Override
    public boolean exiteVertice(E elem) {
        return buscarVertice(elem) != null;
    }

    @Override
    public boolean existeArco(E origen, E destino) {
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> nodoOrigen = vertices[0];
        NodoVert<E> nodoDestino = vertices[1];
        return existeArco(nodoOrigen, nodoDestino);
    }

    protected boolean existeArco(NodoVert<E> origen, NodoVert<E> destino) {
        boolean existe = false;

        if (origen != null && destino != null) {
            NodoAdy<E> ady = origen.getPrimerAdy();
            while (!existe && ady != null) {
                existe = ady.getVertice().getElem().equals(destino.getElem());
                ady = ady.getSigAdy();
            }
        }

        return existe;
    }

    @Override
    public boolean existeCamino(E origen, E destino) {
        boolean exito = false;
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino = vertices[1];

        if (vertOrigen != null && vertDestino != null) {
            Lista<E> visitados = new ListaDinamica<>();
            exito = existeCamino(vertOrigen, vertDestino, visitados);
        }

        return exito;
    }

    private boolean existeCamino(NodoVert<E> vert, NodoVert<E> destino, Lista<E> visitados) {
        boolean exito = false;

        if (vert != null) {
            if (vert == destino) {
                exito = true;
            } else {
                visitados.insertar(vert.getElem());
                NodoAdy<E> ady = vert.getPrimerAdy();
                while (!exito && ady != null) {
                    if (!visitados.existe(ady.getVertice().getElem())) {
                        exito = existeCamino(ady.getVertice(), destino, visitados);
                    }
                    ady = ady.getSigAdy();
                }
            }
        }

        return exito;
    }

    @Override
    public Lista<E> caminoMasCorto(E origen, E destino) {
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino = vertices[1];
        ListaDinamica<E> visitados = new ListaDinamica<>();
        ListaDinamica<E> camino = new ListaDinamica<>();
        int[] distanciaMinima = {Integer.MAX_VALUE}; // Se usa un arreglo para mantener la referencia y poder modificarlo.

        if (vertOrigen != null && vertDestino != null) {
            camino = caminoMasCorto(vertOrigen, vertDestino, visitados, 0, camino, distanciaMinima);
        }
        return camino;
    }

    protected ListaDinamica<E> caminoMasCorto(NodoVert<E> vertice,
                                              NodoVert<E> destino,
                                              ListaDinamica<E> visitados,
                                              int distActual,
                                              ListaDinamica<E> camino,
                                              int[] distMin) {
        if (distActual < distMin[0]) {
            visitados.insertar(vertice.getElem());
            if (vertice == destino) {
                camino = visitados.clone();
                distMin[0] = distActual;
            } else {
                NodoAdy<E> ady = vertice.getPrimerAdy();
                while (ady != null) {
                    if (!visitados.existe(ady.getVertice().getElem())) {

                        camino = caminoMasCorto(
                                ady.getVertice(),
                                destino,
                                visitados,
                                distActual + ady.getEtiqueta(),
                                camino,
                                distMin);
                    }
                    ady = ady.getSigAdy();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
        return camino;
    }

    @Override
    public Lista<E> caminoConMenosVertices(E origen, E destino) {
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino = vertices[1];
        ListaDinamica<E> visitados = new ListaDinamica<>();
        ListaDinamica<E> camino = new ListaDinamica<>();
        int[] distanciaMinima = {Integer.MAX_VALUE}; // Se usa un arreglo para mantener la referencia y poder modificarlo.

        if (vertOrigen != null && vertDestino != null) {
            camino = caminoConMenosVertices(vertOrigen, vertDestino, visitados, 0, camino, distanciaMinima);
        }
        return camino;
    }

    private ListaDinamica<E> caminoConMenosVertices(NodoVert<E> vertice,
                                                    NodoVert<E> destino,
                                                    ListaDinamica<E> visitados,
                                                    int distActual,
                                                    ListaDinamica<E> camino,
                                                    int[] distMin) {
        if (distActual < distMin[0]) {
            visitados.insertar(vertice.getElem());
            if (vertice == destino) {
                camino = visitados.clone();
                distMin[0] = distActual;
            } else {
                NodoAdy<E> ady = vertice.getPrimerAdy();
                while (ady != null) {
                    if (!visitados.existe(ady.getVertice().getElem())) {

                        camino = caminoConMenosVertices(
                                ady.getVertice(),
                                destino,
                                visitados,
                                distActual + 1,
                                camino,
                                distMin);
                    }
                    ady = ady.getSigAdy();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
        return camino;
    }

    @Override
    public Lista<E> caminoMasCorto(E origen, E destino1, E destino2) {
        NodoVert<E>[] vertices = buscarTresVertices(origen, destino1, destino2);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino1 = vertices[1];
        NodoVert<E> vertDestino2 = vertices[2];
        ListaDinamica<E> visitados = new ListaDinamica<>();
        ListaDinamica<NodoAdy<E>> arcosVisitados = new ListaDinamica<>();
        ListaDinamica<E> camino = new ListaDinamica<>();
        int[] distanciaMinima = {Integer.MAX_VALUE}; // Se usa un arreglo para mantener la referencia y poder modificarlo.
        boolean[] bandera = {false}; // si paso por el primer destino.

        if (vertOrigen != null && vertDestino1 != null && vertDestino2 != null) {
            if (destino1 != destino2) {
                camino = caminoMasCorto(
                        vertOrigen,
                        vertDestino1,
                        vertDestino2,
                        visitados,
                        arcosVisitados,
                        0,
                        camino,
                        distanciaMinima,
                        bandera);
            } else {
                camino = caminoMasCorto(vertOrigen, vertDestino1, visitados, 0, camino, distanciaMinima);
            }
        }
        return camino;
    }

    private ListaDinamica<E> caminoMasCorto(NodoVert<E> vertice,
                                            NodoVert<E> destino1,
                                            NodoVert<E> destino2,
                                            ListaDinamica<E> visitados,
                                            ListaDinamica<NodoAdy<E>> arcosVisitados,
                                            int distActual,
                                            ListaDinamica<E> camino,
                                            int[] distMin,
                                            boolean[] bandera) {
        if (distActual < distMin[0]) {
            visitados.insertar(vertice.getElem());

            if (bandera[0] && vertice == destino2) { // si llego al final
                camino = visitados.clone();
                distMin[0] = distActual;
            } else {
                if (!bandera[0] && vertice == destino1) { // si llego a la mitad
                    bandera[0] = true;
                }
                NodoAdy<E> ady = vertice.getPrimerAdy();
                while (ady != null) {
                    if (!arcosVisitados.existe(ady)) { // mientras no haya pasado por el mismo arco
                        arcosVisitados.insertar(ady);

                        camino = caminoMasCorto(
                                ady.getVertice(),
                                destino1,
                                destino2,
                                visitados,
                                arcosVisitados,
                                distActual + ady.getEtiqueta(),
                                camino,
                                distMin,
                                bandera);

                        if (bandera[0] && ady.getVertice() == destino1) // por si volvio de la mitad, ese camino no sirve
                            bandera[0] = false;
                        arcosVisitados.eliminar(arcosVisitados.longitud());
                    }
                    ady = ady.getSigAdy();
                }
            }

            visitados.eliminar(visitados.longitud());
        }
        return camino;
    }

    @Override
    public Lista<E> caminoMasLargo(E origen, E destino) {
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino = vertices[1];
        ListaDinamica<E> vertVisitados = new ListaDinamica<>();
        ListaDinamica<NodoAdy<E>> arcosVisitados = new ListaDinamica<>();
        ListaDinamica<E> camino = new ListaDinamica<>();
        int[] distanciaMaxima = {Integer.MIN_VALUE};

        if (vertOrigen != null && vertDestino != null) {
            camino = caminoMasLargo(
                    vertOrigen,
                    vertDestino,
                    vertVisitados,
                    arcosVisitados,
                    0,
                    camino,
                    distanciaMaxima);
        }
        return camino;
    }

    private ListaDinamica<E> caminoMasLargo(NodoVert<E> vertice,
                                            NodoVert<E> destino,
                                            ListaDinamica<E> vertVisitados,
                                            ListaDinamica<NodoAdy<E>> arcosVisitados,
                                            int distActual,
                                            ListaDinamica<E> camino,
                                            int[] distMax) {
        vertVisitados.insertar(vertice.getElem());
        if (vertice == destino) { // si llego al destino, los vertices visitados es un posible camino
            if (distActual > distMax[0]) {
                camino = vertVisitados.clone();
                distMax[0] = distActual;
            }
        } else { // mientras sea cualquier vertice menos el destino
            NodoAdy<E> ady = vertice.getPrimerAdy();
            while (ady != null) {
                if (!arcosVisitados.existe(ady)) {
                    arcosVisitados.insertar(ady);

                    camino = caminoMasLargo(
                            ady.getVertice(),
                            destino,
                            vertVisitados,
                            arcosVisitados,
                            distActual + ady.getEtiqueta(),
                            camino,
                            distMax);

                    arcosVisitados.eliminar(arcosVisitados.longitud());
                }
                ady = ady.getSigAdy();
            }
        }
        vertVisitados.eliminar(vertVisitados.longitud());

        return camino;
    }

    @Override
    public Lista<Lista<E>> caminosPosibles(E origen, E destino) {
        NodoVert<E>[] vertices = buscarDosVertices(origen, destino);
        NodoVert<E> vertOrigen = vertices[0];
        NodoVert<E> vertDestino = vertices[1];
        ListaDinamica<E> visitados = new ListaDinamica<>();
        Lista<Lista<E>> caminos = new ListaDinamica<>();

        if (vertOrigen != null & vertDestino != null) {
            caminosPosibles(vertOrigen, vertDestino, visitados, caminos);
        }
        return caminos;
    }

    private void caminosPosibles(NodoVert<E> vertice,
                                 NodoVert<E> destino,
                                 ListaDinamica<E> visitados,
                                 Lista<Lista<E>> caminos) {
        visitados.insertar(vertice.getElem());
        if (vertice == destino) {
            caminos.insertar(visitados.clone());
        } else {
            NodoAdy<E> ady = vertice.getPrimerAdy();
            while (ady != null) {
                if (!visitados.existe(ady.getVertice().getElem())) {
                    caminosPosibles(ady.getVertice(), destino, visitados, caminos);
                }
                ady = ady.getSigAdy();
            }
        }
        visitados.eliminar(visitados.longitud());
    }

    @Override
    public Lista<E> listarEnProfundidad() {
        Lista<E> visitados = new ListaDinamica<>();
        NodoVert<E> aux = this.inicio;
        while (aux != null) {
            if (!visitados.existe(aux.getElem())) {
                listarEnProfundidad(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidad(NodoVert<E> nodo, Lista<E> visitados) {
        if (nodo != null) {
            visitados.insertar(nodo.getElem());
            NodoAdy<E> ady = nodo.getPrimerAdy();
            while (ady != null) {
                if (!visitados.existe(ady.getVertice().getElem())) {
                    listarEnProfundidad(ady.getVertice(), visitados);
                }
                ady = ady.getSigAdy();
            }
        }
    }

    @Override
    public Lista<E> listarEnAnchura() {
        Lista<E> visitados = new ListaDinamica<>();
        NodoVert<E> vert = inicio;
        while (vert != null) {
            if (!visitados.existe(vert.getElem())) {
                listarEnAnchura(vert, visitados);
            }
            vert = vert.getSigVertice();
        }
        return visitados;
    }

    private void listarEnAnchura(NodoVert<E> vertIni, Lista<E> visitados) {
        // TODO Mal, y si el vertice adyacente ya esta en la cola???? lo vuelve a agregar, ojo!!!
        Cola<NodoVert<E>> porVisitar = new ColaDinamica<>();
        NodoVert<E> vert;
        NodoAdy<E> ady;

        porVisitar.poner(vertIni);
        while (!porVisitar.esVacia()) {
            vert = porVisitar.obtenerFrente();
            porVisitar.sacar();
            visitados.insertar(vert.getElem());
            ady = vert.getPrimerAdy();
            while (ady != null) {
                // y si el vertice ya esta en la cola????
                if (!visitados.existe(ady.getVertice().getElem())) {
                    porVisitar.poner(ady.getVertice());
                }
                ady = ady.getSigAdy();
            }
        }
    }

    public Lista<Arco<E, Integer>> listarArcos() {
        Lista<Arco<E, Integer>> arcos = new ListaDinamica<>();
        NodoVert<E> vert = this.inicio;

        while (vert != null) {
            NodoAdy<E> ady = vert.getPrimerAdy();
            while (ady != null) {
                arcos.insertar(new Arco<>(vert.getElem(), ady.getVertice().getElem(), ady.getEtiqueta()));
                ady = ady.getSigAdy();
            }
            vert = vert.getSigVertice();
        }

        return arcos;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO equals()
        return super.equals(obj);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // TODO clone()
        return super.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GrafoEtiquetado{inicio=");
        if (inicio != null) sb.append(inicio.getElem());
        else sb.append("null");
        sb.append(",\n");
        NodoVert<E> vertice = inicio;
        NodoAdy<E> ady;

        while (vertice != null) {
            sb.append('\t').append(vertice.getElem()).append(" -> ");
            ady = vertice.getPrimerAdy();
            while (ady != null) {
                sb.append(ady.getVertice().getElem()).append(" (").append(ady.getEtiqueta()).append("), ");
                ady = ady.getSigAdy();
            }
            vertice = vertice.getSigVertice();
            sb.append("\n");
        }

        return sb.append("}").toString();
    }

    @Override
    public boolean esVacio() {
        return inicio == null;
    }

    @Override
    public void vaciar() {
        this.inicio = null;
    }

    protected static class NodoVert<E> implements Serializable {
        private E elem;
        private NodoVert<E> sigVertice;
        private NodoAdy<E> primerAdy;

        public NodoVert(E elem) {
            this(elem, null);
        }

        public NodoVert(E elem, NodoVert<E> sigVertice) {
            this.elem = elem;
            this.sigVertice = sigVertice;
            this.primerAdy = null;
        }

        public E getElem() {
            return elem;
        }

        public void setElem(E elem) {
            this.elem = elem;
        }

        public NodoVert<E> getSigVertice() {
            return sigVertice;
        }

        public void setSigVertice(NodoVert<E> sigVertice) {
            this.sigVertice = sigVertice;
        }

        public NodoAdy<E> getPrimerAdy() {
            return primerAdy;
        }

        public void setPrimerAdy(NodoAdy<E> primerAdy) {
            this.primerAdy = primerAdy;
        }

        public boolean tieneSigVertice() {
            return sigVertice != null;
        }

        public boolean tienePrimerAdyacente() {
            return primerAdy != null;
        }

        @Override
        public String toString() {
            return "NodoVert{" +
                    "elem=" + elem +
                    '}';
        }
    }

    protected static class NodoAdy<E> implements Serializable {
        private NodoVert<E> vertice;
        private NodoAdy<E> sigAdy;
        private int etiqueta;

        public NodoAdy(NodoVert<E> vertice, NodoAdy<E> sigAdy, int etiqueta) {
            this.vertice = vertice;
            this.sigAdy = sigAdy;
            this.etiqueta = etiqueta;
        }

        public NodoVert<E> getVertice() {
            return vertice;
        }

        public void setVertice(NodoVert<E> vertice) {
            this.vertice = vertice;
        }

        public NodoAdy<E> getSigAdy() {
            return sigAdy;
        }

        public void setSigAdy(NodoAdy<E> sigAdy) {
            this.sigAdy = sigAdy;
        }

        public boolean tieneSigAdyacente() {
            return sigAdy != null;
        }

        public int getEtiqueta() {
            return etiqueta;
        }

        public void setEtiqueta(int etiqueta) {
            this.etiqueta = etiqueta;
        }

        @Override
        public String toString() {
            return "NodoAdy{" +
                    "vertice=" + vertice.getElem() +
                    ", etiqueta=" + etiqueta +
                    '}';
        }
    }
}
