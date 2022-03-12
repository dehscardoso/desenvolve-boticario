from google.cloud import bigquery

client = bigquery.Client(project="modified-ripsaw-343701")

schema = [
    bigquery.SchemaField("CODIGO","INTEGER"),
    bigquery.SchemaField("DESCRITOR","STRING"),
    bigquery.SchemaField("DATA","DATE"),
    bigquery.SchemaField("CASADO","BOOLEAN")
    ]

table_id = "modified-ripsaw-343701.sucos.CLIENTES_PYTHON"
table = bigquery.Table(table_id, schema)
table = client.create_table(table)

print("TABELA CRIADA {}.{}.{}".format(table.project, table.dataset_id, table.table_id))