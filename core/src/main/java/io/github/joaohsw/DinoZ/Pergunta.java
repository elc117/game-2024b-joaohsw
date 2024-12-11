package io.github.joaohsw.DinoZ;

public class Pergunta {
    String texto;
    String[] opcoes;
    int resposta_correta;


    Pergunta(String texto, String[] opcoes, int resposta_correta) {
        this.texto = texto;
        this.opcoes = opcoes;
        this.resposta_correta = resposta_correta;
    }

    public static Pergunta[] perguntas = new Pergunta[] {
        new Pergunta("Em qual estado brasileiro está localizado o Geoparque Quarta Colônia?", new String[] {"Rio Grande do Sul", "Santa Catarina", "Paraná", "Minas Gerais"}, 0),
        new Pergunta("Qual município é considerado a sede do Geoparque Quarta Colônia?", new String[] {"Santa Maria", "São João do Polêsine", "Restinga Sêca", "Faxinal do Soturno"}, 1),
        new Pergunta("Qual é um dos principais fósseis encontrados no Geoparque Quarta Colônia?", new String[] {"Dinossauros", "Tubarões pré-históricos", "Fósseis de dicinodontes", "Fósseis de plantas"}, 2),
        new Pergunta("O Geoparque Caçapava do Sul é famoso por qual tipo de formação geológica?", new String[] {"Montanhas de granito", "Cânions", "Cavernas de calcário", "Dunas de areia"}, 0),
        new Pergunta("Qual é o significado de um território ser reconhecido como Geoparque?", new String[] {"Área para mineração intensiva", "Preservação geológica e desenvolvimento sustentável", "Uso exclusivo para turismo", "Exploração de combustíveis fósseis"}, 1),
        new Pergunta("Quantos municípios fazem parte do Geoparque Quarta Colônia?", new String[] {"5", "7", "9", "11"}, 2),
        new Pergunta("Qual é um dos objetivos principais dos geoparques?", new String[] {"Expandir áreas urbanas", "Educação ambiental e valorização cultural", "Explorar recursos naturais", "Preservar espécies ameaçadas"}, 3),
        new Pergunta("O Geoparque Quarta Colônia possui destaque por qual característica natural?", new String[] {"Cavernas subterrâneas", "Dunas costeiras", "Corais fossilizados", "Cachoeiras e matas nativas"}, 3),
        new Pergunta("O Geoparque Caçapava do Sul é conhecido pela formação rochosa chamada:", new String[] {"Cânion do Itaibezinho", "Morro do Diabo", "Pedra do Segredo", "Gruta Azul"}, 2),
        new Pergunta("Qual é o órgão responsável por reconhecer e apoiar os geoparques no mundo?", new String[] {"UNESCO", "WWF", "Greenpeace", "IBAMA"}, 0),
        new Pergunta("Qual é uma das principais atividades turísticas promovidas no Geoparque Quarta Colônia?", new String[] {"Pesca esportiva", "Caminhadas ecológicas", "Esportes radicais", "Observação de aves"}, 1),
        new Pergunta("Qual é o principal objetivo da rede de geoparques criada pela UNESCO?", new String[] {"Construir infraestruturas para mineração", "Expandir áreas urbanas próximas a geoparques", "Promover agricultura intensiva", "Proteger patrimônios culturais e geológicos"}, 3),
        new Pergunta("O Geoparque Caçapava do Sul é parte de qual importante cadeia de montanhas brasileira?", new String[] {"Serra do Mar", "Serra Geral", "Planalto Meridional", "Serra Gaúcha"}, 1),
        new Pergunta("A Pedra do Segredo, formação famosa no Geoparque Caçapava do Sul, é composta principalmente de qual rocha?", new String[] {"Arenito", "Basalto", "Granito", "Calcário"}, 2),
        new Pergunta("Qual é a importância da paleontologia no contexto do Geoparque Quarta Colônia?", new String[] {"Identificar novas espécies de plantas", "Estudar fósseis de animais pré-históricos", "Explorar recursos minerais para indústria", "Preservar formações rochosas raras"}, 1),
        new Pergunta("O Geoparque Quarta Colônia está localizado em uma área com grande diversidade de qual recurso natural?", new String[] {"Água doce", "Minerais preciosos", "Flora tropical", "Corais"}, 0),
        new Pergunta("Quais são as formações geológicas mais comuns no Geoparque Caçapava do Sul?", new String[] {"Granitos e quartzitos", "Calcários e dolomitos", "Basaltos e diabásios", "Arenitos e conglomerados"}, 0),
        new Pergunta("O Geoparque Quarta Colônia está integrado a um projeto de pesquisa de qual universidade brasileira?", new String[] {"PUCRS", "UFRGS", "UFSM", "UNISINOS"}, 2),
        new Pergunta("Qual rio importante corta a região do Geoparque Quarta Colônia?", new String[] {"Rio Taquari", "Rio Uruguai", "Rio Íjui", "Rio Jacuí"}, 3),
        new Pergunta("O Geoparque Caçapava do Sul está inserido em qual bioma brasileiro?", new String[] {"Amazônia", "Mata Atlântica", "Cerrado", "Pampa"}, 3),
        new Pergunta("Qual é o período geológico associado aos fósseis encontrados no Geoparque Quarta Colônia?", new String[] {"Triássico", "Jurássico", "Cretáceo", "Devoniano"}, 0),
        new Pergunta("Qual município do Geoparque Quarta Colônia é famoso por suas cachoeiras?", new String[] {"Itaara", "Nova Palma", "Silveira Martins", "Agudo"}, 2),
        new Pergunta("Quais formações geológicas são características do Geoparque Caçapava do Sul?", new String[] {"Granitos e mármores", "Arenitos e quartzitos", "Calcários e dolomitos", "Basaltos e riolitos"}, 1),
        new Pergunta("Qual é o papel das comunidades locais nos geoparques?", new String[] {"Explorar recursos naturais", "Preservar e divulgar o patrimônio", "Construir infraestrutura turística", "Administrar os territórios"}, 1),
        new Pergunta("O Geoparque Caçapava do Sul pertence a qual região do Rio Grande do Sul?", new String[] {"Região Central", "Campanha Gaúcha", "Vale dos Sinos", "Fronteira Oeste"}, 1),
        new Pergunta("Qual é o nome do evento anual que promove os geoparques e a geoconservação?", new String[] {"Semana da Terra", "Dia Internacional dos Geoparques", "Festival da Geodiversidade", "Geoparque Global Day"}, 0),
        new Pergunta("Qual espécie fóssil é destaque nos estudos paleontológicos da Quarta Colônia?", new String[] {"Dinossauros herbívoros", "Dicinodontes", "Tubarões pré-históricos", "Pterossauros"}, 1),
        new Pergunta("Quais atividades de educação ambiental são promovidas no Geoparque Quarta Colônia?", new String[] {"Workshops sobre fósseis", "Corridas de aventura", "Pesquisas de campo exclusivas", "Trilhas em áreas restritas"}, 0),
        new Pergunta("O Geoparque Caçapava do Sul possui qual título em relação ao Rio Grande do Sul?", new String[] {"Capital Gaúcha do Basalto", "Capital Gaúcha dos Minerais", "Capital Gaúcha do Granito", "Capital Gaúcha do Quartzito"}, 3),
        new Pergunta("Os geoparques são reconhecidos por qual tipo de turismo?", new String[] {"Turismo sustentável", "Turismo de aventura", "Turismo religioso", "Turismo urbano"}, 0)        
    };

}