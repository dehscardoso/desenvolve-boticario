from google.cloud import bigquery

client = bigquery.Client(project="modified-ripsaw-343701")
table_id = "modified-ripsaw-343701.sucos.CLIENTES_PYTHON"
client.delete_table(table_id,not_found_ok=True)
print("Tabela excluida com sucesso")