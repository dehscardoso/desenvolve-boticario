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
             programa002();
        }

        static void programa002()
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
    }    
}        