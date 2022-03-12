import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.CsvOptions;
import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.LoadJobConfiguration;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.StandardSQLTypeName;
import com.google.cloud.bigquery.StandardTableDefinition;
import com.google.cloud.bigquery.TableDefinition;
import com.google.cloud.bigquery.TableId;
import com.google.cloud.bigquery.TableInfo;

public class JavaBigQuery {
	
	public static void main(String[] arg)
	{
		//System.out.print("Hello World.");
		//programa001();
		//programa002();
		//programa003();
		//programa004();
		//programaByteBank002();
		//programaByteBank003();
		programaByteBank004();
	}	
	
	static void programa001()
	{
		BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
		String query = "SELECT * FROM `modified-ripsaw-343701.sucos.dim_clientes`";
		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();
		try
		{
			for (FieldValueList row : bigquery.query(queryConfig).iterateAll())
            {
	             String vCPF = row.get("CPF").getStringValue();
	             String vNome = row.get("NOME").getStringValue();
	             System.out.printf("CPF: %s , NOME: %s ", vCPF, vNome);
	             System.out.printf("\n");
            }
		}
		catch (JobException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static void programa002()
	{
		String conjuntoDados = "sucos";
		String nomeTabela = "CLIENTES_JAVA";
		Schema schema = Schema.of(
				      Field.of("CODIGO",StandardSQLTypeName.INT64),
				      Field.of("DESCRITOR",StandardSQLTypeName.STRING),
				      Field.of("DATA",StandardSQLTypeName.DATE),
				      Field.of("CASADO",StandardSQLTypeName.BOOL));
		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			TableId tableid = TableId.of(conjuntoDados, nomeTabela);
			TableDefinition tabledefinition = StandardTableDefinition.of(schema);		
			TableInfo tableinfo = TableInfo.newBuilder(tableid, tabledefinition).build();
			bigquery.create(tableinfo);
			System.out.println("Tabela criada com sucesso");						
		}
		catch (BigQueryException e)
		{
			System.out.println("Tabela não foi criada \n" + e.toString());
		}
	}
	
	static void programaByteBank002()
	{
		String conjuntoDados = "BYTEBANK_JAVA";
		String nomeTabelaAgencias = "AGENCIAS";
		Schema schemaAgencias = Schema.of(
				      Field.of("NUMERO_AGENCIA",StandardSQLTypeName.STRING),
				      Field.of("NOME_AGENCIA",StandardSQLTypeName.STRING));
		String nomeTabelaClientes = "CLIENTES";
		Schema schemaClientes = Schema.of(
				      Field.of("CPF",StandardSQLTypeName.STRING),
				      Field.of("NOME_CLIENTE",StandardSQLTypeName.STRING));
		String nomeTabelaContasCorrente = "CONTAS_CORRENTE";
		Schema schemaContasCorrente = Schema.of(
				      Field.of("NUMERO_CONTA",StandardSQLTypeName.STRING),
				      Field.of("NUMERO_AGENCIA",StandardSQLTypeName.STRING),
				      Field.of("CPF",StandardSQLTypeName.STRING),
				      Field.of("TIPO_CONTA",StandardSQLTypeName.INT64));
		
		
		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			
			TableId tableidAgencias = TableId.of(conjuntoDados, nomeTabelaAgencias);
			TableDefinition tabledefinitionAgencias = StandardTableDefinition.of(schemaAgencias);		
			TableInfo tableinfoAgencias = TableInfo.newBuilder(tableidAgencias, tabledefinitionAgencias).build();
			bigquery.create(tableinfoAgencias);
			
			TableId tableidClientes = TableId.of(conjuntoDados, nomeTabelaClientes);
			TableDefinition tabledefinitionClientes = StandardTableDefinition.of(schemaClientes);		
			TableInfo tableinfoClientes = TableInfo.newBuilder(tableidClientes, tabledefinitionClientes).build();
			bigquery.create(tableinfoClientes);
			
			TableId tableidContasCorrente = TableId.of(conjuntoDados, nomeTabelaContasCorrente);
			TableDefinition tabledefinitionContasCorrente = StandardTableDefinition.of(schemaContasCorrente);		
			TableInfo tableinfoContasCorrente = TableInfo.newBuilder(tableidContasCorrente, tabledefinitionContasCorrente).build();
			bigquery.create(tableinfoContasCorrente);
			
			System.out.println("Tabela criada com sucesso");						
		}
		catch (BigQueryException e)
		{
			System.out.println("Tabela não foi criada \n" + e.toString());
		}
	}
	
	static void programa003()
	{
		String conjuntoDados = "sucos";
		String nomeTabela = "CLIENTES_JAVA";
		Schema schema = Schema.of(
				      Field.of("CODIGO",StandardSQLTypeName.INT64),
				      Field.of("DESCRITOR",StandardSQLTypeName.STRING),
				      Field.of("DATA",StandardSQLTypeName.DATE),
				      Field.of("CASADO",StandardSQLTypeName.BOOL));
		String sourceUri = "gs://modified-ripsaw-343701/externo/CLIENTES_EXTERNO.csv";
		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			CsvOptions csvoptions = CsvOptions.newBuilder().setSkipLeadingRows(1).setFieldDelimiter(",").build();
			TableId tableid = TableId.of(conjuntoDados, nomeTabela);
			LoadJobConfiguration loadConfig = LoadJobConfiguration.newBuilder(tableid, sourceUri, csvoptions).setSchema(schema).build();
			Job job = bigquery.create(JobInfo.of(loadConfig));
			job = job.waitFor();
			if (job.isDone()) {
				System.out.println("CSV foi lido com sucesso através do JOB");
			}
			else {
				System.out.println("Houve um erro ao carregar o CSV: " + job.getStatus().getError());
			}				
		}
		catch (BigQueryException | InterruptedException e)
		{
			System.out.println("Tabela não foi criada \n" + e.toString());
		}
	}
	
	static void programaByteBank003()
	{
		String conjuntoDados = "BYTEBANK_JAVA";
		String nomeTabelaAgencias = "AGENCIAS";
		Schema schemaAgencias = Schema.of(
				      Field.of("NUMERO_AGENCIA",StandardSQLTypeName.STRING),
				      Field.of("NOME_AGENCIA",StandardSQLTypeName.STRING));
		String sourceUriAgencias = "gs://modified-ripsaw-343701/Exercicios/Agencias.csv";
		String nomeTabelaClientes = "CLIENTES";
		Schema schemaClientes = Schema.of(
				      Field.of("CPF",StandardSQLTypeName.STRING),
				      Field.of("NOME_CLIENTE",StandardSQLTypeName.STRING));
		String sourceUriClientes = "gs://modified-ripsaw-343701/Exercicios/Clientes.csv";
		String nomeTabelaContasCorrente = "CONTAS_CORRENTE";
		Schema schemaContasCorrente = Schema.of(
				      Field.of("NUMERO_CONTA",StandardSQLTypeName.STRING),
				      Field.of("NUMERO_AGENCIA",StandardSQLTypeName.STRING),
				      Field.of("CPF",StandardSQLTypeName.STRING),
				      Field.of("TIPO_CONTA",StandardSQLTypeName.INT64));
		String sourceUriContasCorrente = "gs://modified-ripsaw-343701/Exercicios/ContasCorrente.csv";

		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			CsvOptions csvoptions = CsvOptions.newBuilder().setSkipLeadingRows(1).setFieldDelimiter(",").build();
			
			TableId tableidAgencias = TableId.of(conjuntoDados, nomeTabelaAgencias);
			LoadJobConfiguration loadConfigAgencias = LoadJobConfiguration.newBuilder(tableidAgencias, sourceUriAgencias, csvoptions).setSchema(schemaAgencias).build();
			Job jobAgencias = bigquery.create(JobInfo.of(loadConfigAgencias));
			jobAgencias = jobAgencias.waitFor();
			if (jobAgencias.isDone()) {
				System.out.println("CSV AGENCIAS foi lido com sucesso através do JOB");
			}
			else {
				System.out.println("Houve um erro ao carregar o CSV: " + jobAgencias.getStatus().getError());
			}	
			
			TableId tableidClientes = TableId.of(conjuntoDados, nomeTabelaClientes);
			LoadJobConfiguration loadConfigClientes = LoadJobConfiguration.newBuilder(tableidClientes, sourceUriClientes, csvoptions).setSchema(schemaClientes).build();
			Job jobClientes = bigquery.create(JobInfo.of(loadConfigClientes));
			jobClientes = jobClientes.waitFor();
			if (jobClientes.isDone()) {
				System.out.println("CSV Clientes foi lido com sucesso através do JOB");
			}
			else {
				System.out.println("Houve um erro ao carregar o CSV: " + jobClientes.getStatus().getError());
			}
			
			TableId tableidContasCorrente = TableId.of(conjuntoDados, nomeTabelaContasCorrente);
			LoadJobConfiguration loadConfigContasCorrente = LoadJobConfiguration.newBuilder(tableidContasCorrente, sourceUriContasCorrente, csvoptions).setSchema(schemaContasCorrente).build();
			Job jobContasCorrente = bigquery.create(JobInfo.of(loadConfigContasCorrente));
			jobContasCorrente = jobContasCorrente.waitFor();
			if (jobContasCorrente.isDone()) {
				System.out.println("CSV ContasCorrente foi lido com sucesso através do JOB");
			}
			else {
				System.out.println("Houve um erro ao carregar o CSV: " + jobContasCorrente.getStatus().getError());
			}
			
		}
		catch (BigQueryException | InterruptedException e)
		{
			System.out.println("Tabela não foi criada \n" + e.toString());
		}
	}
	
	static void programa004()
	{
		String conjuntoDados = "sucos";
		String nomeTabela = "CLIENTES_JAVA";
		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			boolean sucesso = bigquery.delete(TableId.of(conjuntoDados, nomeTabela));
			if (sucesso)
			{
				System.out.println("Tabela excluida com sucesso");
			}
			else
			{
				System.out.println("Tabela não existente");
			}					
		}
		catch (BigQueryException e)
		{
			System.out.println("Tabela não foi excluida \n" + e.toString());
		}
	}
	
	static void programaByteBank004()
	{
		String conjuntoDados = "BYTEBANK_JAVA";
		String nomeTabelaAgencias = "AGENCIAS";
		String nomeTabelaClientes = "CLIENTES";
		String nomeTabelaContasCorrente = "CONTAS_CORRENTE";
		
		try{
			BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();
			boolean sucessoAgencias = bigquery.delete(TableId.of(conjuntoDados, nomeTabelaAgencias));
			if (sucessoAgencias)
			{
				System.out.println("Tabela Agencias excluida com sucesso");
			}
			else
			{
				System.out.println("Tabela não existente");
			}
			
			boolean sucessoClientes = bigquery.delete(TableId.of(conjuntoDados, nomeTabelaClientes));
			if (sucessoClientes)
			{
				System.out.println("Tabela Clientes excluida com sucesso");
			}
			else
			{
				System.out.println("Tabela não existente");
			}	
			
			boolean sucessoContasCorrente = bigquery.delete(TableId.of(conjuntoDados, nomeTabelaContasCorrente));
			if (sucessoContasCorrente)
			{
				System.out.println("Tabela Contas Corrente excluida com sucesso");
			}
			else
			{
				System.out.println("Tabela não existente");
			}	
			
		}
		catch (BigQueryException e)
		{
			System.out.println("Tabela não foi excluida \n" + e.toString());
		}
	}
	
}
