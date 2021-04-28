#!/bin/bash
clientId=$(az aks show --name onebyone-aks --resource-group onebyone |jq -r .identityProfile.kubeletidentity.clientId)
echo "Client ID : $clientId"
nodeResourceGroup=$(az aks show --name onebyone-aks --resource-group onebyone |jq -r .nodeResourceGroup)
echo "Node resource group : $nodeResourceGroup"
subId=$(az account show | jq -r .id)
echo "Sub ID : $subId"

echo "Creating role assignments..."

az role assignment create --role "Managed Identity Operator" --assignee "$clientId" --scope /subscriptions/"$subId"/resourcegroups/onebyone
az role assignment create --role "Managed Identity Operator" --assignee "$clientId" --scope /subscriptions/"$subId"/resourcegroups/"$nodeResourceGroup"
az role assignment create --role "Virtual Machine Contributor" --assignee "$clientId" --scope /subscriptions/"$subId"/resourcegroups/"$nodeResourceGroup"

echo "Assignments done"
