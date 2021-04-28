#!/bin/bash
clientId=$(az identity show --name onebyone-kv-id --resource-group onebyone |jq -r .clientId)
echo "Client ID : $clientId"
principalId=$(az identity show --name onebyone-kv-id --resource-group onebyone |jq -r .principalId)
echo "Principal ID : $principalId"
subId=$(az account show | jq -r .id)
echo "Sub ID : $subId"

echo "Creating role assignments..."
az role assignment create --role "Reader" --assignee "$principalId" --scope /subscriptions/"$subId"/resourceGroups/onebyone/providers/Microsoft.KeyVault/vaults/onebyone-kv
az keyvault set-policy -n onebyone-kv --secret-permissions get --spn "$clientId"
az keyvault set-policy -n onebyone-kv --key-permissions get --spn "$clientId"
echo "Assignments done"
