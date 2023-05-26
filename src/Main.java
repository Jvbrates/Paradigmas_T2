import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* Implementar os seguintes exercícios usando Streams e Expressões Lambda
*/
public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Hello world!");

        exe1();
        exe2();
        exe3();
        exe4();
        exe5();
    }

    static class habitante{
        public habitante(int salario,  int n_filhos) {
            this.salario = salario;
            this.n_filhos = n_filhos;
        }

        public int getN_filhos() {
            return n_filhos;
        }

        public int getSalario() {
            return salario;
        }

        int salario;
        int n_filhos;



    }

    static public void exe1() {

        int salario, n_filhos;
        Collection<habitante> habitanteCollection = new ArrayList<>();



        //Leitura
        while (true){
            System.out.println("Salário: ");
            salario = input.nextInt();
            if (salario < 0)
                break;
            System.out.println("Número de filhos: ");
            n_filhos = input.nextInt();
            habitanteCollection.add(new habitante(salario,n_filhos));
        }

        System.out.println("Média salarial: "+
                habitanteCollection.parallelStream()
                        .map(habitante::getSalario)
                        .reduce(0, Integer::sum)
                        /
                        habitanteCollection.size());
        System.out.println("Média de filhos: "+
                habitanteCollection.parallelStream()
                        .map(habitante::getN_filhos)
                        .reduce(0, Integer::sum).doubleValue()
                        /
                        habitanteCollection.size());
        System.out.println("Maior salário: "+
                habitanteCollection.parallelStream()
                        .max(
                                Comparator.comparing(habitante::getSalario)
                        )
                        .get()
                        .getSalario()
        );

        System.out.println("Percentual de pessoas com salário até R$1000: "+ (
                (double)(habitanteCollection.stream().filter(p ->  p.getSalario() <= 1000)
                                .count())
                                /habitanteCollection.size()*100 )+ "%"
                                );
    }

    static public void exe2(){
        class candidato{
            String nome;
            int votos;

            candidato(String nome){
                this.nome = nome;
            }

            public void votar(){
                this.votos++;
            }

            @Override
            public String toString() {
                return nome+": "+votos;
            }
        }

        ArrayList<candidato> candidatoCollection = new ArrayList<>();
        candidatoCollection.add(new candidato("Candidato 1"));
        candidatoCollection.add(new candidato("Candidato 2"));
        candidatoCollection.add(new candidato("Candidato 2"));
        candidatoCollection.add(new candidato("Candidato 4"));
        candidatoCollection.add(new candidato("Nulo"));
        candidatoCollection.add(new candidato("Em branco"));
        int entrada;
        do {
            System.out.println("Voto: ");
            entrada = input.nextInt();
            if(entrada < 7 && entrada > 0)
                candidatoCollection.get(entrada-1).votar();

        }while (entrada != 0);

        System.out.println("Contagem de votos");
        candidatoCollection.forEach(System.out::println);








    }

    static public void exe3(){

        class Aluno {
            String codigo;
            ArrayList<Double> notas = new ArrayList<>();

            Aluno(String codigo){
                this.codigo = codigo;
            }

            public String getCodigo() {
                return codigo;
            }

            public ArrayList<Double> getNotas() {
                return notas;
            }

            public void setNotas(double nota) {
                this.notas.add(nota);
            }
        }

        ArrayList<Integer> peso = new ArrayList<>();

        peso.add(7);
        peso.add(3);
        peso.add(3);

        ArrayList<Aluno> Alunos = new ArrayList<>();
        for (int i = 0; i < 5; i++) { //Laço de repetição
            System.out.println("Código aluno");
            Alunos.add(new Aluno(input.nextLine()));
            System.out.println("Escreva as notas do aluno: ");
            for (int j = 0; j < peso.size(); j++)
                Alunos.get(i).setNotas(input.nextDouble());

            input.nextLine();//Limpa o buffer?

        }

        Alunos.forEach(Aluno -> {
            Iterator<Integer> it = peso.iterator();
            double mediafinal =
                    Aluno.getNotas().stream().sorted(Comparator.reverseOrder()) //Ordena a partir da maior nota
                            .reduce(0., (sum, p) -> sum + p * it.next()) //Soma das notas multiplicadas pelos respectivos pesos
                            /
                            peso.stream().reduce(0, Integer::sum); //Soma dos pesos

            System.out.println("Aluno: " + Aluno.getCodigo());
            if(mediafinal < 5.0)
                System.out.print("REPROVADO " );
            else
                System.out.print("APROVADO ");
            System.out.println(mediafinal);
        });



    }

    static public void exe4() {


        ArrayList<Integer> integers = new ArrayList<>();


        for (int i = 0; i < 10; i++) //Lendo 10 inteiros - Laço de repetição
            integers.add(input.nextInt());

        @FunctionalInterface
        interface Interval<T, U, V> {
            void interval(T t, U u, V v);
        }

        Interval<ArrayList<Integer>, Integer, Integer> method = (n, min, max) -> {
             System.out.println(
                     n.stream()
                             .filter(p -> p< max && p > min)
                             .collect(Collectors.toList())
             );
        };

        method.interval(integers, 0, 25);
        method.interval(integers, 26, 50);
        method.interval(integers, 51, 76);
        method.interval(integers, 76, 100);

    }

    static public void exe5(){

        class Produto {
            int codigo;
            double valor;

            public Produto(int codigo, double valor) {
                this.codigo = codigo;
                this.valor = valor;
            }

            public int getCodigo() {
                return codigo;
            }

            public void setCodigo(int codigo) {
                this.codigo = codigo;
            }

            public double getValor() {
                return valor;
            }

            public void setValor(double valor) {
                this.valor = valor;
            }

            @Override
            public String toString() {
                return "Código: "+getCodigo()+"| Valor: "+getValor();
            }
        }

        ArrayList<Produto> Produtos = new ArrayList<>();

        Consumer<Produto> aum20 = (value) -> value.setValor(value.getValor()*1.2);
        int code = 0;

        while (true){//Laço de repetição

            System.out.println("Indique o código do produto( menor que 0 para sair):");
            code = input.nextInt();

            if(code < 0)
                break;

            System.out.println("Indique o valor do produto:");
            Produtos.add(
                    new Produto(code, input.nextDouble()
                    )
            );

        }

        System.out.println("Valor sem reajuste:");
        Produtos.forEach(System.out::println);

        //Atualiza valores
        Produtos.parallelStream().forEach(aum20);

        System.out.println("Valor novo:");
        Produtos.forEach(System.out::println);

        System.out.println(
            "Média: "+
            Produtos.stream().map(Produto::getValor).reduce(0., Double::sum)/Produtos.size()
        );

    }
}
