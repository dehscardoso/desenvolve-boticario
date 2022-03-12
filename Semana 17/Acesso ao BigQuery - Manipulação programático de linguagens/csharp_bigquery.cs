using Google.Apis.Bigquery.v2.Data;
using Google.Cloud.BigQuery.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSharpBigQuery
{
    class Program
    {
        static void Main(string[] args)
        {
            // Console.WriteLine("Hello World!");
            // Console.ReadLine();

            //programa001();
            //programa002();
            //programa003();
            //programa004();

            //programaByteBank002();
            // programaByteBank003();
            programaByteBank004();
        }

        static void programa001()
        {
            string projetoID = "modified-ripsaw-343701";
            var cliente = BigQueryClient.Create(projetoID);
            string consultaSQL = "SELECT CPF, NOME FROM `modified-ripsaw-343701.sucos.dim_clientes` ";
            var resultadoSQL = cliente.ExecuteQuery(consultaSQL, null);
            foreach (var linha in resultadoSQL)
            {
                Console.WriteLine("CPF: " + linha["CPF"] + " , NOME: " + linha["NOME"]);
            }
            Console.ReadLine();
        }

        static void programa002()
        {
            String conjuntoDados = "sucos";
            String nomeTabela = "CLIENTES_CSHARP";
            String projetoID = "modified-ripsaw-343701";

            BigQueryClient cliente = BigQueryClient.Create(projetoID);
            BigQueryDataset dataset = cliente.GetDataset(conjuntoDados);
            TableSchema schema = new TableSchemaBuilder
            {
                { "CODIGO", BigQueryDbType.Int64 },
                { "DESCRITOR", BigQueryDbType.String},
                { "DATA", BigQueryDbType.Date},
                { "CASADO", BigQueryDbType.Bool}
            }.Build();

            dataset.CreateTable(nomeTabela, schema);
            Console.WriteLine("Tabela criada com sucesso");
            Console.ReadLine();
        }

        static void programaByteBank002()
        {
            String conjuntoDados = "BYTEBANK_CSHARP";
            String projetoID = "modified-ripsaw-343701";
            BigQueryClient cliente = BigQueryClient.Create(projetoID);
            BigQueryDataset dataset = cliente.GetDataset(conjuntoDados);

            String nomeTabelaAgencia = "AGENCIAS";
            TableSchema schemaAgencia = new TableSchemaBuilder
            {
                { "NUMERO_AGENCIA", BigQueryDbType.String },
                { "NOME_AGENCIA", BigQueryDbType.String}
            }.Build();

            String nomeTabelaCliente = "CLIENTES";
            TableSchema schemaCliente = new TableSchemaBuilder
            {
                { "CPF", BigQueryDbType.String },
                { "NOME_CLIENTE", BigQueryDbType.String}
            }.Build();

            String nomeTabelaContaCorrente = "CONTAS_CORRENTE";
            TableSchema schemaContaCorrente = new TableSchemaBuilder
            {
                { "NUMERO_CONTA", BigQueryDbType.String },
                { "NUMERO_AGENCIA", BigQueryDbType.String },
                { "CPF", BigQueryDbType.String },
                { "TIPO_CONTA", BigQueryDbType.Int64 }
            }.Build();

            dataset.CreateTable(nomeTabelaAgencia, schemaAgencia);
            dataset.CreateTable(nomeTabelaCliente, schemaCliente);
            dataset.CreateTable(nomeTabelaContaCorrente, schemaContaCorrente);

            Console.WriteLine("Tabelas criadas com sucesso");
            Console.ReadLine();
        }

        static void programa003()
        {
            String conjuntoDados = "sucos";
            String nomeTabela = "CLIENTES_CSHARP";
            String projetoID = "modified-ripsaw-343701";

            BigQueryClient cliente = BigQueryClient.Create(projetoID);
            var gcsURI = "gs://modified-ripsaw-343701/externo/CLIENTES_EXTERNO.csv";
            BigQueryDataset dataset = cliente.GetDataset(conjuntoDados);
            TableSchema schema = new TableSchemaBuilder
            {
                { "CODIGO", BigQueryDbType.Int64 },
                { "DESCRITOR", BigQueryDbType.String},
                { "DATA", BigQueryDbType.Date},
                { "CASADO", BigQueryDbType.Bool}
            }.Build();

            var destinationTableRef = dataset.GetTableReference(nomeTabela);
            var jobOptions = new CreateLoadJobOptions()
            {
                SourceFormat = FileFormat.Csv,
                FieldDelimiter = ",",
                SkipLeadingRows = 1
            };
            var loadJob = cliente.CreateLoadJob(gcsURI, destinationTableRef, schema, jobOptions);
            loadJob.PollUntilCompleted();
            BigQueryTable table = cliente.GetTable(destinationTableRef);
            Console.WriteLine($"Carregada {table.Resource.NumRows} linhas na tabela {table.FullyQualifiedId}");
            Console.ReadLine();
        }

        static void programaByteBank003()
        {
            String conjuntoDados = "BYTEBANK_CSHARP";
            String projetoID = "modified-ripsaw-343701";
            BigQueryClient cliente = BigQueryClient.Create(projetoID);
            BigQueryDataset dataset = cliente.GetDataset(conjuntoDados);

            String nomeTabelaAgencia = "AGENCIAS";
            TableSchema schemaAgencia = new TableSchemaBuilder
            {
                { "NUMERO_AGENCIA", BigQueryDbType.String },
                { "NOME_AGENCIA", BigQueryDbType.String}
            }.Build();
            var destinationTableRefAgencia = dataset.GetTableReference(nomeTabelaAgencia);
            var gcsURIAgencia = "gs://modified-ripsaw-343701/Exercicios/Agencias.csv";

            String nomeTabelaCliente = "CLIENTES";
            TableSchema schemaCliente = new TableSchemaBuilder
            {
                { "CPF", BigQueryDbType.String },
                { "NOME_CLIENTE", BigQueryDbType.String}
            }.Build();
            var destinationTableRefCliente = dataset.GetTableReference(nomeTabelaCliente);
            var gcsURICliente = "gs://modified-ripsaw-343701/Exercicios/Clientes.csv";

            String nomeTabelaContaCorrente = "CONTAS_CORRENTE";
            TableSchema schemaContaCorrente = new TableSchemaBuilder
            {
                { "NUMERO_CONTA", BigQueryDbType.String },
                { "NUMERO_AGENCIA", BigQueryDbType.String },
                { "CPF", BigQueryDbType.String },
                { "TIPO_CONTA", BigQueryDbType.Int64 }
            }.Build();
            var destinationTableRefContaCorrente = dataset.GetTableReference(nomeTabelaContaCorrente);
            var gcsURIContaCorrente = "gs://modified-ripsaw-343701/Exercicios/ContasCorrente.csv";

            var jobOptions = new CreateLoadJobOptions()
            {
                SourceFormat = FileFormat.Csv,
                FieldDelimiter = ",",
                SkipLeadingRows = 1
            };

            var loadJobAgencia = cliente.CreateLoadJob(gcsURIAgencia, destinationTableRefAgencia, schemaAgencia, jobOptions);
            loadJobAgencia.PollUntilCompleted();
            var loadJobCliente = cliente.CreateLoadJob(gcsURICliente, destinationTableRefCliente, schemaCliente, jobOptions);
            loadJobCliente.PollUntilCompleted();
            var loadJobContaCorrente = cliente.CreateLoadJob(gcsURIContaCorrente, destinationTableRefContaCorrente, schemaContaCorrente, jobOptions);
            loadJobContaCorrente.PollUntilCompleted();

            Console.WriteLine("Tabelas carregadas com sucesso");
            Console.ReadLine();
        }

        static void programa004()
        {
            String conjuntoDados = "sucos";
            String nomeTabela = "CLIENTES_CSHARP";
            String projetoID = "modified-ripsaw-343701";

            BigQueryClient cliente = BigQueryClient.Create(projetoID);
            cliente.DeleteTable(conjuntoDados, nomeTabela);
            Console.WriteLine("Tabela removida com sucesso");
            Console.ReadLine();
        }

        static void programaByteBank004()
        {
            String conjuntoDados = "BYTEBANK_CSHARP";
            String projetoID = "modified-ripsaw-343701";
            BigQueryClient cliente = BigQueryClient.Create(projetoID);

            String nomeTabelaAgencia = "AGENCIAS";
            String nomeTabelaCliente = "CLIENTES";
            String nomeTabelaContaCorrente = "CONTAS_CORRENTE";
           
            cliente.DeleteTable(conjuntoDados, nomeTabelaAgencia);
            cliente.DeleteTable(conjuntoDados, nomeTabelaCliente);
            cliente.DeleteTable(conjuntoDados, nomeTabelaContaCorrente);

            Console.WriteLine("Tabelas removidas com sucesso");
            Console.ReadLine();
        }
    }
}
