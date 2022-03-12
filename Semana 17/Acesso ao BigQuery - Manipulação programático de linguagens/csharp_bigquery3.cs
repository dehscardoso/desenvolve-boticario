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
             programa004();
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
    }       
}        