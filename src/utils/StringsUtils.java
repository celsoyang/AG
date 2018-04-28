/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.scene.text.Font;

/**
 *
 * @author Celso Souza
 */
public class StringsUtils {

    public static final String FUNCIONARIOS = "Funcionários";
    public static final String ATIVIDADES = "Atividades";
    public static final String INICIAR = "Iniciar";
    public static final String CANCELAR = "Cancelar";
    public static final String VAZIA = "";
    public static final String NOME = "Nome";
    public static final String CARGO = "Cargo";
    public static final String TEMPO_EXPERIENCIA = "Tempo de Experência";
    public static final String TEMPO_PROJETO = "Tempo de Projeto";
    public static final String AREA = "Área";
    public static final String DOIS_PONTOS = ":";
    public static final String SALVAR = "Salvar";
    public static final String AVALIAR = "Avaliar";
    public static final String START = "Start";
    public static final String ADICIONAR = "Adicionar";
    public static final String LISTAR = "Listar";
    public static final String OPCOES = "Opções";
    public static final String GERAR_POPULACAO = "Gerar população";
    public static final String ASSOCIAR_ATIVIDADES = "Associuar Atividades";
    public static final Font FONTE_SISTEMA = Font.font("Georgia", 13);
    public static final String PATH_FUNCIONARIOS = "../files/funcionarios.func";
    public static final String PATH_ATIVIDADES = "../files/atividades.atv";
    public static final String LIMPAR = "Limpar";
    public static final String DRIVER_POSTGRE = "org.postgresql.Driver";
    public static final String POSTGRE = "PostgreSql";
    public static final String SQL_SERVER = "SqlServer";
    public static final String ENTITY_MANAGER = "AG";
    public static final String MSG_SALVO_SUCESSO = "Salvo com Sucesso";
    public static final String MSG_DELETADO_SUCESSO = "Deletado com Sucesso";
    public static final String MSG_ERRO_PROCESSO = "Problema ao Realizar Processo";
    public static final String MSG_CONFIRMA_EXCLUSAO = "Confirma Exclusão?";
    public static final String MSG_INFORME_IDIVIDUOS = "Informe a quantidade de indivíduos";
    public static final String MSG_GERADOS = "Gerados";
    public static final String MSG_INFORME_CODIGO = "Informe o código";
    
    /******************************************************************************************************/
    /******************************************************************************************************/
    /*SELECT*/
    
    public static final String SELECT_FUNCIONARIO = "select f from Funcionario f";
    public static String SELECT_ATIVIDADE = "select at from Atividade at";
    
    /******************************************************************************************************/
    /******************************************************************************************************/

    public static final String[] NOMES_FUNC = {"João", "Pedro", "Bernardo", "Cristiano", "Gustavo", "Rafael", 
        "Mário", "Antônio", "Everton", "José", "Julino", "Renato", "Agnaldo"};
    public static final String[] SOBRENOMES_FUNC = {"Vasconcelos", "Souza", "Silva",
        "Oliveira", "Menezes", "Santos", "Costa", "Albuquerque", "Alves", "Dias", "Mendes", "Filho", "Bernardes"};    
    
}
