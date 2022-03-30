class PessoaComTelefone extends Pessoa {

    private int numTel;
    private Telefone[] tel;

    public PessoaComTelefone(int id, String nome) {
        super(id, nome);
        numTel = 0;
    }


    public String toString() {

        String aux = super.toString();
        for (int i = 0; i < numTel; i++) {
            aux = aux + "\n\tTel[" + (i + 1) + "]" + tel[i].toString();

        }

        return aux;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
        tel = new Telefone[numTel];
    }

    public int getNumTel() {
        return numTel;
    }

    public void setTel(int i, Telefone t) {
        if (i > -1 && i < numTel) {
            tel[i] = t;
        }
    }

    public Telefone getTel(int i){
        return ((i>-1 && i<numTel)? tel[i]:null);
    }
}