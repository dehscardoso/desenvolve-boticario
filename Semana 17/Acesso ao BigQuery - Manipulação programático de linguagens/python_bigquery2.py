from google.cloud import bigquery

client = bigquery.Client(project="modified-ripsaw-343701")

schema = [
    bigquery.SchemaField("CODIGO","INTEGER"),
    bigquery.SchemaField("DESCRITOR","STRING"),
    bigquery.SchemaField("DATA","DATE"),
    bigquery.SchemaField("CASADO","BOOLEAN")
    ]

table_id = "modified-ripsaw-343701.sucos.CLIENTES_PYTHON"
uri = "gs://modified-ripsaw-343701/externo/CLIENTES_EXTERNO.csv"
job_config = bigquery.LoadJobConfig(schema=schema, skip_leading_rows=1, source_format=bigquery.SourceFormat.CSV)

load_job = client.load_table_from_uri(source_uris=uri, destination=table_id, job_config=job_config)
load_job.result()

print("TABELA CARREGADA {}".format(table_id))